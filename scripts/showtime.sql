-- Ambil durasi dari movie
-- Misal movie_id 1 = 120 menit, dst.
-- Insert 10 jadwal tayang (showtime)

INSERT INTO public.showtime (
  movie_id, hall_id, price, start_time, end_time,
  secure_id, created_at, updated_at
) VALUES
(1, 1, 50000, '2025-05-20 10:00:00', '2025-05-20 12:00:00', gen_random_uuid(), now(), now()),
(1, 1, 50000, '2025-05-20 14:00:00', '2025-05-20 16:00:00', gen_random_uuid(), now(), now()),
(2, 1, 60000, '2025-05-20 17:00:00', '2025-05-20 19:00:00', gen_random_uuid(), now(), now()),
(3, 2, 70000, '2025-05-20 13:00:00', '2025-05-20 15:30:00', gen_random_uuid(), now(), now()),
(4, 2, 55000, '2025-05-20 16:00:00', '2025-05-20 18:00:00', gen_random_uuid(), now(), now()),
(5, 3, 75000, '2025-05-20 10:30:00', '2025-05-20 12:30:00', gen_random_uuid(), now(), now()),
(5, 3, 75000, '2025-05-20 13:00:00', '2025-05-20 15:00:00', gen_random_uuid(), now(), now()),
(2, 3, 65000, '2025-05-20 15:30:00', '2025-05-20 17:30:00', gen_random_uuid(), now(), now()),
(3, 1, 80000, '2025-05-20 18:00:00', '2025-05-20 20:30:00', gen_random_uuid(), now(), now()),
(1, 2, 50000, '2025-05-20 20:30:00', '2025-05-20 22:30:00', gen_random_uuid(), now(), now());
