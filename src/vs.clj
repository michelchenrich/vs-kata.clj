(ns vs)

(defn- extended-rent-pricer [base-rate, expected-days, penalty]
  (fn [days]
    (+ base-rate
       (* penalty (max (- days expected-days) 0)))))

(defn- movie [title, year, pricer, pointer]
  {:title title, :year year, :pricer pricer, :pointer pointer})

(defn childrens-movie [title, year]
  (movie title, year,
         (extended-rent-pricer 1.25, 3, 1.0),
         (constantly 1.0)))

(defn regular-movie [title, year]
  (movie title, year,
         (extended-rent-pricer 1.75, 2, 1.5),
         (constantly 1.0)))

(defn new-release [title, year]
  (movie title, year,
         (fn [days] (* 3.0 days)),
         (fn [days] (float days))))

(defn rental [movie days]
  {:title         (:title movie),
   :year          (:year movie),
   :days          days,
   :price         ((:pricer movie) days),
   :renter-points ((:pointer movie) days)})

(defn- sum [collection property]
  (reduce + (map property collection)))

(defn statement [items]
  {:items         items,
   :total         (sum items :price),
   :renter-points (sum items :renter-points)})