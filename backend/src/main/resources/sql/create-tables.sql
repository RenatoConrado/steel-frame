CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Client

CREATE TABLE clients (
    id            uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id     varchar(150) NOT NULL,
    client_secret varchar(400) NOT NULL,
    redirect_uri  varchar(200) NOT NULL,
    scope         varchar(50)
);

-- Users and Permissions
CREATE TABLE roles (
    id   serial PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id         uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    username   varchar(100) NOT NULL UNIQUE,
    email      varchar(255) NOT NULL UNIQUE,
    password   varchar(255) NOT NULL,
    full_name  varchar(255),
    created_at timestamp with time zone DEFAULT NOW()
);

CREATE TABLE user_roles (
    user_id uuid REFERENCES users(id) ON DELETE CASCADE,
    role_id int REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
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
    course_id uuid REFERENCES courses(id) ON DELETE CASCADE,
    title     varchar(255) NOT NULL,
    position  int,
    CONSTRAINT uq_module_position UNIQUE (course_id, position)
);

CREATE INDEX idx_modules_course_id ON modules(course_id);

CREATE TABLE lessons (
    id        uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    module_id uuid REFERENCES modules(id) ON DELETE CASCADE,
    title     varchar(255) NOT NULL,
    content   text,
    video_url text,
    position  int,
    duration  int,
    CONSTRAINT uq_lesson_position UNIQUE (module_id, position)
);

CREATE INDEX idx_lessons_module_id ON lessons(module_id);

-- Course Students
CREATE TABLE enrollments (
    id          uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    user_id     uuid REFERENCES users(id) ON DELETE CASCADE,
    course_id   uuid REFERENCES courses(id) ON DELETE CASCADE,
    enrolled_at timestamp with time zone DEFAULT NOW(),
    status      varchar(50)              DEFAULT 'ACTIVE',
    UNIQUE (user_id, course_id)
);

CREATE INDEX idx_enrollments_user_id ON enrollments(user_id);

CREATE INDEX idx_enrollments_course_id ON enrollments(course_id);

CREATE TABLE progress (
    id            uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    enrollment_id uuid REFERENCES enrollments(id) ON DELETE CASCADE,
    lesson_id     uuid REFERENCES lessons(id) ON DELETE CASCADE,
    completed     boolean          DEFAULT FALSE,
    completed_at  timestamp with time zone
);

CREATE INDEX idx_progress_enrollment_id ON progress(enrollment_id);

CREATE INDEX idx_progress_lesson_id ON progress(lesson_id);

-- Course Rating and Comments
CREATE TABLE ratings (
    id         serial PRIMARY KEY,
    course_id  uuid REFERENCES courses(id) ON DELETE CASCADE,
    user_id    uuid REFERENCES users(id) ON DELETE CASCADE,
    rating     int CHECK ( rating >= 1 AND rating <= 5 ),
    comment    text,
    created_at timestamp with time zone DEFAULT NOW(),
    UNIQUE (course_id, user_id)
);

CREATE INDEX idx_ratings_course_id ON ratings(course_id);

CREATE INDEX idx_ratings_user_id ON ratings(user_id);

CREATE TABLE course_comments (
    id                uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    course_id         uuid NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
    user_id           uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    parent_comment_id uuid REFERENCES course_comments(id) ON DELETE CASCADE,
    content           text NOT NULL,
    created_at        timestamp with time zone DEFAULT NOW(),
    updated_at        timestamp with time zone DEFAULT NOW()
);

CREATE INDEX idx_course_comments_course_id ON course_comments(course_id);

CREATE TABLE lesson_comments (
    id                uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    lesson_id         uuid NOT NULL REFERENCES lessons(id) ON DELETE CASCADE,
    user_id           uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    parent_comment_id uuid REFERENCES lesson_comments(id) ON DELETE CASCADE,
    content           text NOT NULL,
    created_at        timestamp with time zone DEFAULT NOW(),
    updated_at        timestamp with time zone DEFAULT NOW()
);

CREATE INDEX idx_lesson_comments_lesson_id ON lesson_comments(lesson_id);
