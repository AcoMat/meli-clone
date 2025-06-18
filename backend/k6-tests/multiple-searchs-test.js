import http from 'k6/http';
import { check } from 'k6';

export let options = {
    vus: 10,
    duration: '30s',
};

export default function () {
    const res = http.get('http://localhost:8080/products/search?q=sansung');
    check(res, {
        'status is 200': (r) => r.status === 200,
        'response time < 3500ms': (r) => r.timings.duration < 3500,
    });
}
