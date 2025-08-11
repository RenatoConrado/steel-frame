CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Users, Clients and Permissions
CREATE TABLE roles (
    id   uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar(50) NOT NULL UNIQUE
);

INSERT INTO roles(name) VALUES ('USER'), ('ADMIN');

CREATE TABLE clients (
    id            uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id     varchar(150) NOT NULL,
    client_secret varchar(400) NOT NULL,
    redirect_uri  varchar(200) NOT NULL,
    scope_id      uuid REFERENCES roles(id)
);

CREATE TABLE users (
    id         uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    username   varchar(100) NOT NULL UNIQUE,
    email      varchar(255) NOT NULL UNIQUE,
    password   varchar(72)  NOT NULL, -- bcrypt
    full_name  varchar(255),
    created_at timestamp with time zone DEFAULT NOW(),
    role_id    uuid REFERENCES roles(id)
);

-- Course structure
CREATE TABLE courses (
    id            uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    title         varchar(255) NOT NULL,
    description   text,
    instructor_id uuid REFERENCES users(id),
    published     boolean                  DEFAULT FALSE,
    created_at    timestamp with time zone DEFAULT NOW()
);

CREATE INDEX idx_courses_instructor_id ON courses(instructor_id);

CREATE TABLE modules (
    id        uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    title     varchar(255) NOT NULL,
    position  int,
    course_id uuid REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT uq_module_position UNIQUE (course_id, position)
);

CREATE INDEX idx_modules_course_id ON modules(course_id);

CREATE TABLE lessons (
    id        uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    title     varchar(255) NOT NULL,
    content   text,
    video_url text,
    position  int,
    duration  int,
    module_id uuid REFERENCES modules(id) ON DELETE CASCADE,
    CONSTRAINT uq_lesson_position UNIQUE (module_id, position)
);

CREATE INDEX idx_lessons_module_id ON lessons(module_id);

-- Course Students
CREATE TABLE enrollments (
    id          uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    enrolled_at timestamp with time zone DEFAULT NOW(),
    status      varchar(50)              DEFAULT 'ACTIVE',
    user_id     uuid REFERENCES users(id) ON DELETE CASCADE,
    course_id   uuid REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT uq_user_course UNIQUE (user_id, course_id)
);

CREATE INDEX idx_enrollments_user_id ON enrollments(user_id);

CREATE INDEX idx_enrollments_course_id ON enrollments(course_id);

CREATE TABLE progress (
    id            uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    completed     boolean          DEFAULT FALSE,
    completed_at  timestamp with time zone,
    enrollment_id uuid REFERENCES enrollments(id) ON DELETE CASCADE,
    lesson_id     uuid REFERENCES lessons(id) ON DELETE CASCADE,
    CONSTRAINT uq_progress_enrollment_lesson UNIQUE (enrollment_id, lesson_id)
);

CREATE INDEX idx_progress_enrollment_id ON progress(enrollment_id);

CREATE INDEX idx_progress_lesson_id ON progress(lesson_id);

-- Feedback
CREATE TABLE ratings (
    id         uuid PRIMARY KEY,
    rating     int CHECK ( rating >= 1 AND rating <= 5 ),
    comment    text,
    created_at timestamp with time zone DEFAULT NOW(),
    course_id  uuid UNIQUE REFERENCES courses(id) ON DELETE CASCADE,
    user_id    uuid UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_ratings_course_id ON ratings(course_id);

CREATE INDEX idx_ratings_user_id ON ratings(user_id);

CREATE TABLE course_comments (
    id                uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    content           text NOT NULL,
    created_at        timestamp with time zone DEFAULT NOW(),
    updated_at        timestamp with time zone DEFAULT NOW(),
    course_id         uuid NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
    user_id           uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    parent_comment_id uuid REFERENCES course_comments(id) ON DELETE CASCADE
);

CREATE INDEX idx_course_comments_course_id ON course_comments(course_id);

CREATE TABLE lesson_comments (
    id                uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    content           text NOT NULL,
    created_at        timestamp with time zone DEFAULT NOW(),
    updated_at        timestamp with time zone DEFAULT NOW(),
    lesson_id         uuid NOT NULL REFERENCES lessons(id) ON DELETE CASCADE,
    user_id           uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    parent_comment_id uuid REFERENCES lesson_comments(id) ON DELETE CASCADE
);

CREATE INDEX idx_lesson_comments_lesson_id ON lesson_comments(lesson_id);
