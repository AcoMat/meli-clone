import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    iterations: 10,
};

export default function () {
    http.get('http://localhost:15173');

    sleep(1);
}