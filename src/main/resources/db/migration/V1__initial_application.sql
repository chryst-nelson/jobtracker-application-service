CREATE TABLE IF NOT EXISTS job_applications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    job_title VARCHAR(100) NOT NULL,
    job_url VARCHAR(500),
    salary_expectation DECIMAL(10,2),
    status VARCHAR(30) NOT NULL DEFAULT 'APPLIED',
    applied_date DATE NOT NULL,
    deadline DATE,
    notes TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);