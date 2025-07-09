import http from 'k6/http';
import { check } from 'k6';
import { randomItem } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

const searchQueries = [
    'samsung', 'iphone', 'xiaomi', 'motorola', 'huawei', 'nokia', 'sony', 'lg', 'google', 'oneplus'
];

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';

export let options = {
    vus: 25,
    duration: '25s',
    thresholds: {
        'http_req_duration': ['p(95)<3500'],
        'http_req_failed': ['rate<0.01'],
        'checks': ['rate>0.99'],
    },
};

export default function () {
    const query = randomItem(searchQueries);
    const res = http.get(`${BASE_URL}/products/search?q=${query}`);
    check(res, {
        'status is 200': (r) => r.status === 200,
    });

    if (res.status !== 200) {
        console.error(`Request failed for query "${query}". Status: ${res.status}, Body: ${res.body}`);
    }
}
