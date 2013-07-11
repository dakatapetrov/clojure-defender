(ns clojure-defender.physics.geometry
  (:use [clojure.contrib.math])
  (:require [clojure-defender.globals :as gl]))

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
  (sqrt (+ (expt w 2)
           (expt h 2))))

(defn rect-radius-length
  [w h]
  (/ (rect-diagonal-length w h) 2))

(defn distance
  [x1 y1 x2 y2]
  (sqrt (+ (expt (- x1 x2) 2)
           (expt (- y1 y2) 2))))

(defn sphere-colision?
  [x1 y1 r1 x2 y2 r2]
  (<= (distance x1 y1 x2 y2)
      (+ r1 r2)))

(defn in-range?
  [x1 y1 x2 y2 r2]
  (<= (+ (expt (- x2 x1) 2)
         (expt (- y2 y1) 2))
      (expt r2 2)))

(defn on-object
  [x y objects]
  (first (filter #(in-rect? x
                            y
                            (:x %)
                            (:y %)
                            gl/path-size
                            gl/path-size)
                 objects)))
