import http from 'k6/http';
import { check, sleep } from 'k6';
import { vu } from 'k6/execution';
import { BASE_URL, productsIds } from '../config.js';

export const options = {
    vus: 100,
    stages: [
        { duration: '10s', target: 50 },  // empieza tranquilo
        { duration: '20s', target: 500 }, // sube a 500
        { duration: '30s', target: 500 }, // mantiene
        { duration: '10s', target: 0 },   // baja
    ],
    thresholds: {
        http_req_duration: ['p(95)<4000'], // 95% of requests should complete within 3s
        http_req_failed: ['rate<0.05'], // Less than 5% of requests should fail
        checks: ['rate>0.95'], // 95% of checks should pass
    },
};

function randomString(length) {
    const chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let result = '';
    for (let i = 0; i < length; i++) {
        result += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return result;
}

function randomEmail() {
    return `e2e_${vu.idInTest}_${Date.now()}_${randomString(5)}@email.com`;
}

function randomUser() {
    return {
        firstName: randomString(6),
        lastName: randomString(8),
        email: randomEmail(),
        password: randomString(10)
    };
}

export default function() {
    // Each VU registers a unique user
    const uniqueUser = randomUser();
    const payload = JSON.stringify(uniqueUser);
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };
    const startRegister = Date.now();
    const response = http.post(`${BASE_URL}/auth/register`, payload, params);
    const registerDuration = Date.now() - startRegister;
    if (registerDuration > 3000) {
        console.warn(`[VU ${vu.idInTest}] Registration request took ${registerDuration} ms (>3s) for user: ${uniqueUser.email}`);
    }
    check(response, {
        'user registered': (r) => r.status === 200
    });
    if (response.status !== 200) {
        console.error(`[VU ${vu.idInTest}] Registration failed for ${uniqueUser.email}`);
        console.error(`[VU ${vu.idInTest}] Status: ${response.status}`);
        console.error(`[VU ${vu.idInTest}] Response body: ${response.body}`);
        return; // Exit if registration fails
    }
    sleep(1);

    // Continue with the rest of the test using the registered user
    const productId = productsIds[Math.floor(Math.random() * productsIds.length)];
    console.log(`[VU ${vu.idInTest}] Starting e2e flow with user: ${uniqueUser.email}, product: ${productId}`);

    // Step 1: Login
    const loginPayload = JSON.stringify({
        email: uniqueUser.email,
        password: uniqueUser.password
    });

    const loginParams = {
        headers: {
            'Content-Type': 'application/json',
        },
    };
    const startLogin = Date.now();
    const loginResponse = http.post(`${BASE_URL}/auth/login`, loginPayload, loginParams);
    const loginDuration = Date.now() - startLogin;
    if (loginDuration > 3000) {
        console.warn(`[VU ${vu.idInTest}] Login request took ${loginDuration} ms (>3s) for user: ${uniqueUser.email}`);
    }

    check(loginResponse, {
        'login successful': (r) => r.status === 200,
        'login returns user data': (r) => r.status === 200 && r.body && JSON.parse(r.body).firstName !== undefined,
    });

    if (loginResponse.status !== 200) {
        console.error(`[VU ${vu.idInTest}] Login failed for ${uniqueUser.email}`);
        console.error(`[VU ${vu.idInTest}] Status: ${loginResponse.status}`);
        console.error(`[VU ${vu.idInTest}] Response body: ${loginResponse.body}`);
        return;
    }

    // Extract the token from the Authorization header
    const authToken = loginResponse.headers['Authorization'];
    if (!authToken) {
        console.error(`[VU ${vu.idInTest}] No Authorization header found in login response`);
        return;
    }

    const authHeaders = {
        'Content-Type': 'application/json',
        'Authorization': authToken, // Use the header directly (already includes "Bearer ")
    };

    sleep(1);

    // Step 2: Add product to favorites
    const favoritePayload = JSON.stringify({
        productId: productId
    });

    const favoriteParams = {
        headers: authHeaders,
    };
    const startFavorite = Date.now();
    const favoriteResponse = http.put(`${BASE_URL}/favorites`, favoritePayload, favoriteParams);
    const favoriteDuration = Date.now() - startFavorite;
    if (favoriteDuration > 3000) {
        console.warn(`[VU ${vu.idInTest}] Add to favorites request took ${favoriteDuration} ms (>3s) for user: ${uniqueUser.email}`);
    }

    check(favoriteResponse, {
        'add to favorites successful': (r) => r.status === 200,
    });

    if (favoriteResponse.status !== 200) {
        console.error(`[VU ${vu.idInTest}] Add to favorites FAILED for ${uniqueUser.email}`);
        console.error(`[VU ${vu.idInTest}] Status: ${favoriteResponse.status}`);
        console.error(`[VU ${vu.idInTest}] Response body: ${favoriteResponse.body}`);
        console.error(`[VU ${vu.idInTest}] Request payload: ${favoritePayload}`);
    }

    sleep(1);

    // Step 3: Purchase the product
    const purchasePayload = JSON.stringify({
        items: [{
            productId: productId,
            amount: Math.floor(Math.random() * 3) + 1 // Random quantity between 1-3
        }]
    });

    const purchaseParams = {
        headers: authHeaders,
    };
    const startPurchase = Date.now();
    const purchaseResponse = http.post(`${BASE_URL}/purchases`, purchasePayload, purchaseParams);
    const purchaseDuration = Date.now() - startPurchase;
    if (purchaseDuration > 3000) {
        console.warn(`[VU ${vu.idInTest}] Purchase request took ${purchaseDuration} ms (>3s) for user: ${uniqueUser.email}`);
    }

    check(purchaseResponse, {
        'purchase successful': (r) => r.status === 200,
        'purchase response contains success message': (r) => r.body && r.body.includes('successfully'),
    });

    if (purchaseResponse.status !== 200) {
        console.error(`[VU ${vu.idInTest}] Purchase FAILED for ${uniqueUser.email}`);
        console.error(`[VU ${vu.idInTest}] Status: ${purchaseResponse.status}`);
        console.error(`[VU ${vu.idInTest}] Response body: ${purchaseResponse.body}`);
        console.error(`[VU ${vu.idInTest}] Request payload: ${purchasePayload}`);
        console.error(`[VU ${vu.idInTest}] Auth headers: ${JSON.stringify(authHeaders)}`);
    }

    sleep(1);

    // Step 4: Post a review for the purchased product
    const reviewPayload = JSON.stringify({
        rating: Math.floor(Math.random() * 5) + 1, // Random rating between 1-5
        review: `Great product! Testing review from user ${uniqueUser.firstName}. Quality is excellent and delivery was fast.`
    });

    const reviewParams = {
        headers: authHeaders,
    };
    const startReview = Date.now();
    const reviewResponse = http.post(`${BASE_URL}/products/${productId}/reviews`, reviewPayload, reviewParams);
    const reviewDuration = Date.now() - startReview;
    if (reviewDuration > 3000) {
        console.warn(`[VU ${vu.idInTest}] Review post request took ${reviewDuration} ms (>3s) for user: ${uniqueUser.email}`);
    }

    check(reviewResponse, {
        'review post successful': (r) => r.status === 200,
        'review response contains success message': (r) => r.body && r.body.includes('successfully'),
    });

    if (reviewResponse.status !== 200) {
        console.error(`[VU ${vu.idInTest}] Review post FAILED for ${uniqueUser.email}`);
        console.error(`[VU ${vu.idInTest}] Status: ${reviewResponse.status}`);
        console.error(`[VU ${vu.idInTest}] Response body: ${reviewResponse.body}`);
        console.error(`[VU ${vu.idInTest}] Request payload: ${reviewPayload}`);
        console.error(`[VU ${vu.idInTest}] Product ID: ${productId}`);
        console.error(`[VU ${vu.idInTest}] Auth headers: ${JSON.stringify(authHeaders)}`);
    }

    sleep(1);

    // Step 5: Verify user profile and purchases
    const startProfile = Date.now();
    const profileResponse = http.get(`${BASE_URL}/profile`, { headers: authHeaders });
    const profileDuration = Date.now() - startProfile;
    if (profileDuration > 3000) {
        console.warn(`[VU ${vu.idInTest}] Profile retrieval request took ${profileDuration} ms (>3s) for user: ${uniqueUser.email}`);
    }

    check(profileResponse, {
        'profile retrieval successful': (r) => r.status === 200,
        'profile contains user data': (r) => r.body && JSON.parse(r.body).firstName === uniqueUser.firstName,
    });

    if (profileResponse.status !== 200) {
        console.error(`[VU ${vu.idInTest}] Profile retrieval FAILED for ${uniqueUser.email}`);
        console.error(`[VU ${vu.idInTest}] Status: ${profileResponse.status}`);
        console.error(`[VU ${vu.idInTest}] Response body: ${profileResponse.body}`);
    }

    const startPurchases = Date.now();
    const purchasesResponse = http.get(`${BASE_URL}/purchases`, { headers: authHeaders });
    const purchasesDuration = Date.now() - startPurchases;
    if (purchasesDuration > 3000) {
        console.warn(`[VU ${vu.idInTest}] Purchases retrieval request took ${purchasesDuration} ms (>3s) for user: ${uniqueUser.email}`);
    }

    check(purchasesResponse, {
        'purchases retrieval successful': (r) => r.status === 200,
        'purchases contain data': (r) => r.body && Array.isArray(JSON.parse(r.body)),
    });

    if (purchasesResponse.status !== 200) {
        console.error(`[VU ${vu.idInTest}] Purchases retrieval FAILED for ${uniqueUser.email}`);
        console.error(`[VU ${vu.idInTest}] Status: ${purchasesResponse.status}`);
        console.error(`[VU ${vu.idInTest}] Response body: ${purchasesResponse.body}`);
    }

    console.log(`[VU ${vu.idInTest}] Completed e2e flow for user: ${uniqueUser.email}`);

    sleep(2); // Wait before next iteration
}