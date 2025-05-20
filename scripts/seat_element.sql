INSERT INTO public.seat_element (element, secure_id, created_at, updated_at)
VALUES
('SEAT', gen_random_uuid(), now(), now()),
('SEAT_EMPTY', gen_random_uuid(), now(), now()),
('SCREEN', gen_random_uuid(), now(), now()),
('WALKWAY', gen_random_uuid(), now(), now());
