(ns clojure-defender.physics.geometry)
(require '[clojure-defender.globals :as gl])

(defn exp
  [x n]
  (reduce * (repeat n x)))

(defn abs
  [x]
  (if (neg? x) (- x) x))

(defn in-rect?
  [x1 y1 x2 y2 w2 h2]
  (let [not-pos? (complement pos?)
        not-neg? (complement neg?)]
    (and (not-pos? (compare x2 x1))
         (not-neg? (compare (+ x2 w2) x1))
         (not-pos? (compare y2 y1))
         (not-neg? (compare (+ y2 h2) y1)))))

(defn sub-vect
  [x1 y1 x2 y2]
  (let [svx (- x2 x1)
        svy (- y2 y1)]
    [svx svy]))

(defn normalized-vector
  [x y]
  (let [m (max (abs x) (abs y))
        nx (/ x m)
        ny (/ y m)]
    [nx ny]))

(defn rect-center
  [x y w h]
  (let [cx (+ x (/ w 2))
        cy (+ y (/ h 2))]
    [cx cy]))

(defn rect-diagonal-length
  [w h]
  (+ (exp w 2)
     (exp h 2)))

(defn rect-radius-length
  [w h]
  (/ (rect-diagonal-length w h) 2))

(defn distance
  [x1 y1 x2 y2]
  (+ (exp (- x1 x2) 2)
     (exp (- y1 y2) 2)))

(defn sphere-colision?
  [x1 y1 r1 x2 y2 r2]
  (<= (distance x1 y1 x2 y2)
      (+ r1 r2)))

(defn in-range?
  [x1 y1 x2 y2 r2]
  (<= (+ (exp (- x2 x1) 2)
         (exp (- y2 y1) 2))
      (exp r2 2)))

(defn on-object
  [x y objects]
  (first (filter #(in-rect? x
                            y
                            (:x %)
                            (:y %)
                            gl/path-size
                            gl/path-size)
                 objects)))
