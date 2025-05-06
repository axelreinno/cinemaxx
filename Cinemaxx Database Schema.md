```
Table city {
  id int [pk, increment]
  name varchar(100)
  province varchar(100)
}

Table cinema {
  id int [pk, increment]
  name varchar(100)
  address text
  city_id int [ref: > city.id]
}

Table cinema_hall_type {
  id int [pk, increment]
  type varchar(50)
}

Table cinema_hall {
  id int [pk, increment]
  cinema_id int [ref: > cinema.id]
  name varchar(50)
  hall_type_id int [ref: > cinema_hall_type.id]
}

Table seat_element {
  id int [pk, increment]
  element varchar(50)
}

Table seat {
  id int [pk, increment]
  hall_id int [ref: > cinema_hall.id]
  seat_element_id int [ref: > seat_element.id]
  row_index int
  col_index int
  label varchar
  status enum('ACTIVE', 'INACTIVE')
}

Table genre {
  id int [pk, increment]
  genre varchar(50)
}

Table movie {
  id int [pk, increment]
  title varchar(150)
  description text
  duration_min int
  release_date date
  director varchar(150)
  age_rating varchar(10)
  status enum("UPCOMING", "NOW_PLAYING")
}

Table movie_genre {
  id int [pk, increment]
  movie_id int [ref: > movie.id]
  genre_id int [ref: > genre.id]
}

Table movie_rating {
  id int [pk, increment]
  movie_id int [ref: > movie.id]
  user_id int [ref: > user.id]
  rating decimal(2,1)
  review text
  rating_time datetime
}

Table showtime {
  id int [pk, increment]
  movie_id int [ref: > movie.id]
  hall_id int [ref: > cinema_hall.id]
  start_time datetime
  end_time datetime
  price decimal(10,2)
}

Table user {
  id int [pk, increment]
  name varchar(100)
  email varchar(100)
  phone varchar(20)
}

Table booking {
  id int [pk, increment]
  user_id int [ref: > user.id]
  show_id int [ref: > showtime.id]
  booking_time datetime
  status varchar(20)
  total_amount decimal(10,2)
}

Table booking_seat {
  id int [pk, increment]
  booking_id int [ref: > booking.id]
  seat_id int [ref: > seat.id]
  price decimal(10,2)
}

Table payment {
  id int [pk, increment]
  booking_id int [ref: > booking.id]
  amount decimal(10,2)
  payment_time datetime
  payment_method varchar(30)
  payment_status varchar(20)
}
```