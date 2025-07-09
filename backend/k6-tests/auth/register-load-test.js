import http from 'k6/http';
import { check, sleep } from 'k6';
import { vu } from 'k6/execution';

export const options = {
    vus: 5, // Reduced to 5 virtual users to avoid overwhelming the database
    duration: '30s', // Run for 30 seconds
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

let iterationCounter = 0;

export default function () {
    // Increment counter for each iteration
    iterationCounter++;

    // Select a random user from the list
    const user = users[Math.floor(Math.random() * users.length)];

    // Create truly unique email using VU ID, iteration counter, and timestamp
    const uniqueId = `${vu.idInTest}${iterationCounter}${Date.now()}${Math.floor(Math.random() * 10000)}`;
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

    console.log(`VU${vu.idInTest}-Iter${iterationCounter}: Registering user: ${uniqueUser.firstName} ${uniqueUser.lastName} with email: ${uniqueUser.email}`);

    const response = http.post(`${BASE_URL}/auth/register`, payload, params);

    const isSuccess = check(response, {
        'registration status is 200': (r) => r.status === 200,
        'response contains authorization header': (r) => r.status === 200 && r.headers['Authorization'] !== undefined,
        'response body contains user data': (r) => {
            if (r.status !== 200) return false;
            try {
                const body = JSON.parse(r.body);
                return body.firstName === uniqueUser.firstName &&
                       body.lastName === uniqueUser.lastName &&
                       body.email === uniqueUser.email;
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
        console.error(`VU${vu.idInTest}: Registration failed for user: ${uniqueUser.email}`);
        console.error(`Response status: ${response.status}`);
        console.error(`Response body: ${response.body}`);
        if (response.error) {
            console.error(`Network error: ${response.error}`);
        }
    } else {
        console.log(`VU${vu.idInTest}: Successfully registered ${uniqueUser.email}`);
    }

    sleep(1); // Wait 1 second between requests to reduce load
}
