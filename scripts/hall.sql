INSERT INTO public.hall (
    "type", secure_id, cinema_id, hall_type_id, row_total, column_total, created_at, updated_at
) VALUES
('HALL REGULAR 1', gen_random_uuid(), 1, 1, 10, 15, now(), now()),
('Hall REGULAR 2', gen_random_uuid(), 1, 2, 12, 18, now(), now()),
('HALL IMAX 1', gen_random_uuid(), 2, 1, 8, 12, now(), now()),
('HALL IMAX 2', gen_random_uuid(), 2, 3, 10, 16, now(), now()),
('HALL 4DX 1', gen_random_uuid(), 3, 4, 14, 20, now(), now()),
('HALL 4DX 2', gen_random_uuid(), 3, 5, 9, 14, now(), now()),
('HALL DOLBY 1', gen_random_uuid(), 4, 1, 11, 17, now(), now()),
('HALL DOLBY 2', gen_random_uuid(), 4, 6, 10, 15, now(), now()),
('HALL GOLD CLASS 1', gen_random_uuid(), 5, 7, 8, 12, now(), now()),
('HALL GOLD CLASS 2', gen_random_uuid(), 5, 8, 9, 13, now(), now()),
('HALL SATIN CLASS 1', gen_random_uuid(), 6, 9, 12, 18, now(), now()),
('HALL SATIN CLASS 2', gen_random_uuid(), 6, 10, 10, 15, now(), now()),
('HALL VELVET CLASS 1', gen_random_uuid(), 7, 2, 8, 11, now(), now()),
('HALL VELVET CLASS 2', gen_random_uuid(), 7, 3, 10, 16, now(), now()),
('HALL SWEETBOX 1', gen_random_uuid(), 8, 4, 11, 17, now(), now()),
('HALL SWEETBOX  2', gen_random_uuid(), 8, 5, 13, 19, now(), now()),
('HALL MX4D  1', gen_random_uuid(), 9, 6, 9, 13, now(), now()),
('HALL MX4D  2', gen_random_uuid(), 9, 7, 14, 21, now(), now()),
('HALL ULTRAXD  1', gen_random_uuid(), 10, 8, 12, 18, now(), now()),
('HALL ULTRAXD  2', gen_random_uuid(), 10, 9, 10, 15, now(), now());