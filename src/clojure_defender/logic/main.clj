(ns clojure-defender.logic.main)

(use 'clojure-defender.gui.main)
(require '[clojure-defender.globals :as gl])

(defn in-rect?
  [x y w h ox oy]
  (and (< x ox)
       (> (+ x w) ox)
       (< y oy)
       (> (+ y h) oy)))

(defn move-enemy
  [enemy]
  (let [speed (:speed @enemy)
        movex (* speed (:dirx @enemy))
        movey (* speed (:diry @enemy))]
    (if (some #(in-rect? (:x %)
                         (:y %)
                         gl/path-size
                         gl/path-size
                         (:x @enemy)
                         (:y @enemy))
              @gl/protect-points)
      (swap! gl/enemies dissoc enemy)
    (dosync
      (do
        (alter enemy update-in [:x] #(+ % movex))
        (alter enemy update-in [:y] #(+ % movey)))))))

(defn step
  []
  (doseq [enemy (keys @gl/enemies)]
    (move-enemy enemy)))


(defn generate
  []
  (doseq [generator @gl/generators]
    (let [x (:x generator)
          y (:y generator)
          enemy (rand-nth (:enemies generator))
          dir (rand-nth (:directions generator))
          dirx (first dir)
          diry (second dir)]
      (swap! gl/enemies
             conj
             {(ref (conj enemy {:x x :y y :dirx dirx :diry diry})) :on}))))

(defn move
  []
 (loop []
   (step)
   (redisplay gl/main-frame)
   (Thread/sleep 3))
   (recur))

(defn regenerate
  []
  (loop []
    (generate)
    (redisplay gl/main-frame)
    (Thread/sleep 1000)
    (recur)))

(defn play
  []
  (.start (Thread. regenerate))
  (.start (Thread. move)))
