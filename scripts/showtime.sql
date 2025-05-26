-- Ambil durasi dari movie
-- Misal movie_id 1 = 120 menit, dst.
-- Insert 10 jadwal tayang (showtime)

INSERT INTO public.showtime (
  movie_id, hall_id, price, start_time, end_time,
  secure_id, created_at, updated_at
) VALUES
(1, 1, 50000, '2025-05-27 10:00:00', '2025-05-27 12:00:00', gen_random_uuid(), now(), now()),
(1, 1, 50000, '2025-05-27 14:00:00', '2025-05-27 16:00:00', gen_random_uuid(), now(), now()),
(1, 1, 50000, '2025-05-27 22:00:00', '2025-05-27 23:50:00', gen_random_uuid(), now(), now()),
(1, 1, 60000, '2025-05-27 17:00:00', '2025-05-27 19:00:00', gen_random_uuid(), now(), now()),
(1, 2, 70000, '2025-05-28 13:00:00', '2025-05-28 15:30:00', gen_random_uuid(), now(), now()),
(1, 2, 55000, '2025-05-28 16:00:00', '2025-05-28 18:00:00', gen_random_uuid(), now(), now()),
(1, 3, 75000, '2025-05-29 10:30:00', '2025-05-29 12:30:00', gen_random_uuid(), now(), now()),
(1, 3, 75000, '2025-05-29 13:00:00', '2025-05-29 15:00:00', gen_random_uuid(), now(), now()),
(1, 3, 65000, '2025-05-29 15:30:00', '2025-05-29 17:30:00', gen_random_uuid(), now(), now()),
(1, 1, 80000, '2025-05-29 18:00:00', '2025-05-29 20:30:00', gen_random_uuid(), now(), now()),
(1, 2, 50000, '2025-05-29 20:30:00', '2025-05-29 22:30:00', gen_random_uuid(), now(), now());
