INSERT INTO app_users (id, email, first_name, is_admin, last_name, password) VALUES (1, 'admin@email.com', 'Matias', TRUE, 'Acosta', '$2a$10$lxNZaZ0mHO3aD/6k3y8Ge.giLaIdgdLFoFt1N.ElEEtrc6SIbMJOW');
INSERT INTO app_users (id, email, first_name, is_admin, last_name, password) VALUES (2, 'juliantrejo@email.com', 'Julian', FALSE, 'Trejo', '$2a$10$R.65lbFnZvMrGFVixopmEO6BXq/s3BE1XiPECVzYRk24NKZpM0TQm');

-- Reset the auto-increment sequence to start from 3
ALTER TABLE app_users ALTER COLUMN id RESTART WITH 3;
