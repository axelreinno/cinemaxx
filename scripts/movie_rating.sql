INSERT INTO public.movie_rating (
    rating, review, movie_id, user_id, secure_id, created_at, updated_at
) VALUES
(4.5, 'Film laga yang seru dan intens.', 1, 1, gen_random_uuid(), now(), now()),
(3.8, 'Kisah keluarga yang menyentuh.', 2, 2, gen_random_uuid(), now(), now()),
(4.9, 'Penutup epik untuk Avengers.', 3, 3, gen_random_uuid(), now(), now()),
(4.7, 'Visual dan cerita sangat kuat.', 4, 4, gen_random_uuid(), now(), now()),
(4.0, 'Film drama yang penuh makna.', 5, 5, gen_random_uuid(), now(), now()),
(4.2, 'Twist yang menarik dan mendalam.', 6, 6, gen_random_uuid(), now(), now()),
(3.5, 'Cocok untuk tontonan ringan.', 7, 7, gen_random_uuid(), now(), now()),
(4.6, 'Nolan never disappoints.', 8, 8, gen_random_uuid(), now(), now()),
(3.9, 'Horror yang membekas.', 9, 9, gen_random_uuid(), now(), now()),
(4.3, 'Romantis dan nostalgia.', 10, 10, gen_random_uuid(), now(), now()),
(4.8, 'Aksi nonstop dari awal hingga akhir.', 1, 11, gen_random_uuid(), now(), now()),
(3.6, NULL, 2, 12, gen_random_uuid(), now(), now()),
(4.9, 'Masterpiece.', 3, 13, gen_random_uuid(), now(), now()),
(4.5, NULL, 4, 14, gen_random_uuid(), now(), now()),
(4.1, 'Sangat relevan dengan kondisi sosial.', 5, 15, gen_random_uuid(), now(), now()),
(3.7, 'Bisa lebih baik dari sisi narasi.', 6, 16, gen_random_uuid(), now(), now()),
(4.4, NULL, 7, 17, gen_random_uuid(), now(), now()),
(4.6, 'Ceritanya mind-blowing!', 8, 18, gen_random_uuid(), now(), now()),
(3.9, NULL, 9, 19, gen_random_uuid(), now(), now()),
(4.2, 'Recommended for couples.', 10, 20, gen_random_uuid(), now(), now());