import http from 'k6/http';
import { check, sleep } from 'k6';
import { vu } from 'k6/execution';

export const options = {
    vus: 5, // 5 virtual users
    duration: '30s', // Run for 30 seconds
    thresholds: {
        http_req_duration: ['p(95)<2000'], // 95% of requests should complete within 2s
        http_req_failed: ['rate<0.01'], // Less than 1% of requests should fail
        checks: ['rate>0.99'], // 99% of checks should pass
    },
};

const BASE_URL = 'http://localhost:8080';

const users = [
    { firstName: 'Julian', lastName: 'Trejo', email: 'juliantrejo@email.com', password: 'julian123' },
    { firstName: 'Douglas', lastName: 'Espagnol', email: 'douglasespagnol@email.com', password: 'douglas123' },
    { firstName: 'Juan', lastName: 'Cruzcenturion', email: 'juancruzcenturion@email.com', password: 'juan123' },
    { firstName: 'Lucas', lastName: 'Dellagiustina', email: 'lucasdellagiustina@email.com', password: 'lucas123' },
    { firstName: 'Juan', lastName: 'Ignaciogarcia', email: 'juanignaciogarcia@email.com', password: 'juan123' },
    { firstName: 'Uriel', lastName: 'Pineyro', email: 'urielpineyro@email.com', password: 'uriel123' },
    { firstName: 'Matias', lastName: 'Laime', email: 'matiaslaime@email.com', password: 'matias123' },
    { firstName: 'Juan', lastName: 'Cabezas', email: 'juancabezas@email.com', password: 'juan123' },
    { firstName: 'Aguero', lastName: 'Mauro', email: 'agueromauro@email.com', password: 'aguero123' },
    { firstName: 'Aguero', lastName: 'Fernando', email: 'aguerofernando@email.com', password: 'aguero123' },
    { firstName: 'Mailin', lastName: 'Sonez', email: 'mailinsonez@email.com', password: 'mailin123' },
    { firstName: 'Carlos', lastName: 'Saldana', email: 'carlossaldana@email.com', password: 'carlos123' },
    { firstName: 'Adrian', lastName: 'Cardozo', email: 'adriancardozo@email.com', password: 'adrian123' },
    { firstName: 'Sofia', lastName: 'Rossi', email: 'sofiarossi@email.com', password: 'sofia123' },
    { firstName: 'Mateo', lastName: 'Diaz', email: 'mateodiaz@email.com', password: 'mateo123' },
    { firstName: 'Valentina', lastName: 'Gomez', email: 'valentinagomez@email.com', password: 'valentina123' },
    { firstName: 'Benjamin', lastName: 'Castro', email: 'benjamincastro@email.com', password: 'benjamin123' },
    { firstName: 'Isabella', lastName: 'Hernandez', email: 'isabellahernandez@email.com', password: 'isabella123' }
];

// Store registered users for login testing
let registeredUsers = [];
let iterationCounter = 0;

export function setup() {
    console.log('Setting up test data - registering users for login testing...');

    // Register a subset of users that will be used for login testing
    const usersToRegister = users.slice(0, 10); // Register first 10 users

    usersToRegister.forEach((user, index) => {
        const uniqueId = `setup${index}${Date.now()}`;
        const uniqueUser = {
            firstName: user.firstName,
            lastName: user.lastName,
            email: `${user.firstName.toLowerCase()}${user.lastName.toLowerCase()}${uniqueId}@email.com`,
            password: user.password
        };

        const payload = JSON.stringify(uniqueUser);
        const params = {
            headers: {
                'Content-Type': 'application/json',
            },
        };

        const response = http.post(`${BASE_URL}/auth/register`, payload, params);

        if (response.status === 200) {
            registeredUsers.push({
                email: uniqueUser.email,
                password: uniqueUser.password,
                firstName: uniqueUser.firstName,
                lastName: uniqueUser.lastName
            });
        } else {
            console.error(`Failed to register setup user: ${uniqueUser.email}, Status: ${response.status}`);
        }

        sleep(0.1); // Small delay between registrations
    });

    console.log(`Setup complete. Registered ${registeredUsers.length} users for testing.`);
    return { registeredUsers };
}

export default function (data) {
    iterationCounter++;

    if (!data.registeredUsers || data.registeredUsers.length === 0) {
        console.error('No registered users available for login testing');
        return;
    }

    // Select a random registered user for login
    const user = data.registeredUsers[Math.floor(Math.random() * data.registeredUsers.length)];

    const loginPayload = JSON.stringify({
        email: user.email,
        password: user.password
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    console.log(`VU${vu.idInTest}-Iter${iterationCounter}: Attempting login for user: ${user.email}`);

    const response = http.post(`${BASE_URL}/auth/login`, loginPayload, params);

    const isSuccess = check(response, {
        'login status is 200': (r) => r.status === 200,
        'response contains authorization header': (r) => r.status === 200 && r.headers['Authorization'] !== undefined,
        'response body contains user data': (r) => {
            if (r.status !== 200) return false;
            try {
                const body = JSON.parse(r.body);
                return body.firstName === user.firstName &&
                       body.lastName === user.lastName &&
                       body.email === user.email;
            } catch (e) {
                console.error(`Failed to parse response body: ${e.message}`);
                return false;
            }
        },
        'authorization header contains Bearer token': (r) => {
            if (r.status !== 200) return false;
            const authHeader = r.headers['Authorization'];
            return authHeader && authHeader.startsWith('Bearer ');
        }
    });

    if (!isSuccess || response.status !== 200) {
        console.error(`VU${vu.idInTest}: Login failed for user: ${user.email}`);
        console.error(`Response status: ${response.status}`);
        console.error(`Response body: ${response.body}`);
        if (response.error) {
            console.error(`Network error: ${response.error}`);
        }
    }
}

