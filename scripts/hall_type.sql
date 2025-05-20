INSERT INTO public.hall_type ("type", secure_id, created_at, updated_at)
VALUES
('Regular', gen_random_uuid(), now(), now()),
('IMAX', gen_random_uuid(), now(), now()),
('4DX', gen_random_uuid(), now(), now()),
('Dolby Atmos', gen_random_uuid(), now(), now()),
('Gold Class', gen_random_uuid(), now(), now()),
('Satin Class', gen_random_uuid(), now(), now()),
('Velvet Class', gen_random_uuid(), now(), now()),
('Sweetbox', gen_random_uuid(), now(), now()),
('MX4D', gen_random_uuid(), now(), now()),
('Ultra XD', gen_random_uuid(), now(), now());