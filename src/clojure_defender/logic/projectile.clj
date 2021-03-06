(ns clojure-defender.logic.projectile
  (:use [clojure-defender.physics.geometry])
  (:require [clojure-defender.globals :as gl]))

(defn- move-by
  [x y speed]
  (let [move-by-x (* x speed)
        move-by-y (* y speed)]
    [move-by-x move-by-y]))

(defn- move-projectile
  [projectile]
  (dosync
    (let [{:keys [x y speed enemy]} @projectile
          ex (:x @enemy)
          ey (:y @enemy)
          [cx cy] (rect-center ex ey gl/enemy-size gl/enemy-size)
          [svx svy] (sub-vect x y cx cy)
          [dirx diry] (normalized-vector svx svy)
          [move-by-x move-by-y] (move-by dirx diry speed)]
      (alter projectile update-in [:x] #(+ % (* speed dirx)))
      (alter projectile update-in [:y] #(+ % (* speed diry))))))

(defn- apply-debuff
  [debuff enemy]
  (let [{:keys [put clear duration]} debuff
        on-debuff? (:debuff @enemy)]
    (when-not on-debuff?
      (dosync
        (alter enemy conj {:debuff (ref debuff)})
        (put enemy)))))

(defn- collision
  [projectile]
  (let [{:keys [x y damage debuff enemy]} @projectile
        ex (:x @enemy)
        ey (:y @enemy)
        [cx cy] (rect-center ex ey gl/enemy-size gl/enemy-size)
        hs (/ gl/enemy-size 2)]
    (when (sphere-colision? x y gl/projectile-size cx cy hs)
      (dosync
        (alter enemy update-in [:hp] #(- % damage))
        (swap! gl/projectiles disj projectile)
        (when debuff
          (apply-debuff debuff enemy))))))

(defn step-projectile
  [projectile]
  (dosync
    (move-projectile projectile)
    (collision projectile)))
