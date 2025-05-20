INSERT INTO public.movie (
    title, director, description, duration_min, release_date,
    age_rating, status, secure_id, created_at, updated_at
) VALUES
('The Raid', 'Gareth Evans', 'A SWAT team becomes trapped in a tenement run by a ruthless mobster.', 101, '2012-03-23', 'R17', 'NOW_SHOWING', gen_random_uuid(), now(), now()),
('Laskar Pelangi', 'Riri Riza', 'A story about ten schoolchildren and their inspiring teachers.', 120, '2008-09-25', 'SU', 'NOW_SHOWING', gen_random_uuid(), now(), now()),
('Avengers: Endgame', 'Anthony Russo', 'The Avengers assemble once more to undo Thanos’s actions.', 181, '2019-04-26', 'R13', 'NOT_AVAILABLE', gen_random_uuid(), now(), now()),
('Interstellar', 'Christopher Nolan', 'A team of explorers travel through a wormhole in space.', 169, '2014-11-07', 'R13', 'COMING_SOON', gen_random_uuid(), now(), now()),
('Nanti Kita Cerita Tentang Hari Ini', 'Angga Dwimas Sasongko', 'Family drama based on the best-selling book.', 121, '2020-01-02', 'R13', 'NOW_SHOWING', gen_random_uuid(), now(), now()),
('Parasite', 'Bong Joon-ho', 'A poor family schemes to become employed by a wealthy one.', 132, '2019-05-30', 'R17', 'NOW_SHOWING', gen_random_uuid(), now(), now()),
('Imperfect', 'Ernest Prakasa', 'A woman struggles with body image and self-acceptance.', 113, '2019-12-19', 'R13', 'NOT_AVAILABLE', gen_random_uuid(), now(), now()),
('Inception', 'Christopher Nolan', 'A thief who steals corporate secrets through dream-sharing.', 148, '2010-07-16', 'R13', 'COMING_SOON', gen_random_uuid(), now(), now()),
('KKN di Desa Penari', 'Awi Suryadi', 'A group of students experiences horror during field work.', 130, '2022-04-30', 'R17', 'NOW_SHOWING', gen_random_uuid(), now(), now()),
('Dilan 1990', 'Fajar Bustomi', 'Teenage romance set in 1990s Bandung.', 110, '2018-01-25', 'R13', 'NOT_AVAILABLE', gen_random_uuid(), now(), now()),
('Top Gun: Maverick', 'Joseph Kosinski', 'After 30 years, Maverick is still pushing the envelope.', 130, '2022-05-27', 'R13', 'NOW_SHOWING', gen_random_uuid(), now(), now()),
('Joko Anwar’s Gundala', 'Joko Anwar', 'The origin story of Indonesia’s lightning-speed hero.', 119, '2019-08-29', 'R13', 'COMING_SOON', gen_random_uuid(), now(), now()),
('Doctor Strange', 'Scott Derrickson', 'A neurosurgeon discovers the mystic arts.', 115, '2016-11-04', 'R13', 'NOT_AVAILABLE', gen_random_uuid(), now(), now()),
('Susi Susanti: Love All', 'Sim F', 'A biopic of Indonesian badminton legend Susi Susanti.', 97, '2019-10-24', 'R13', 'NOW_SHOWING', gen_random_uuid(), now(), now()),
('Cek Toko Sebelah', 'Ernest Prakasa', 'A comedy about Chinese-Indonesian family dynamics.', 104, '2016-12-28', 'R13', 'NOW_SHOWING', gen_random_uuid(), now(), now()),
('Tenet', 'Christopher Nolan', 'A secret agent manipulates time to prevent World War III.', 150, '2020-08-26', 'R17', 'COMING_SOON', gen_random_uuid(), now(), now()),
('Frozen II', 'Chris Buck', 'Elsa, Anna, Kristoff, Olaf and Sven leave Arendelle.', 103, '2019-11-22', 'SU', 'NOW_SHOWING', gen_random_uuid(), now(), now()),
('Pengabdi Setan', 'Joko Anwar', 'A family is haunted by the death of the mother.', 107, '2017-09-28', 'R17', 'NOW_SHOWING', gen_random_uuid(), now(), now()),
('Spirited Away', 'Hayao Miyazaki', 'A girl enters a world of spirits and gods.', 125, '2001-07-20', 'SU', 'NOT_AVAILABLE', gen_random_uuid(), now(), now()),
('The Dark Knight', 'Christopher Nolan', 'Batman faces the Joker in this iconic superhero sequel.', 152, '2008-07-18', 'R13', 'COMING_SOON', gen_random_uuid(), now(), now());
