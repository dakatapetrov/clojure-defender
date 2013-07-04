(ns clojure-defender.logic.main)

(use 'clojure-defender.gui.main)
(require '[clojure-defender.globals :as gl])

(defn in-rect?
  [x y w h ox oy]
  (let [not-pos? (complement pos?)
        not-neg? (complement neg?)]
    (and (not-pos? (compare x ox))
         (not-neg? (compare (+ x w) ox))
         (not-pos? (compare y oy))
         (not-neg? (compare (+ y h) oy)))))

(defn get-path
  [enemy]
  (first (filter #(in-rect? (:x %)
                            (:y %)
                            gl/path-size
                            gl/path-size
                            (:x @enemy)
                            (:y @enemy))
                 @gl/paths)))

(defn on-defend-point?
  [enemy]
  (some #(in-rect? (:x %)
                   (:y %)
                   gl/path-size
                   gl/path-size
                   (:x @enemy)
                   (:y @enemy))
        @gl/defend-points))

(defn move-enemy
  [enemy]
  (let [speed (:speed @enemy)
        path (get-path enemy)
        px (first (:direction path))
        py (second (:direction path))
        movex (* speed px)
        movey (* speed py)]
    (if (on-defend-point? enemy)
      (swap! gl/enemies dissoc enemy)
      (dosync
        (do
          (alter enemy update-in [:x] #(+ % movex))
          (alter enemy update-in [:y] #(+ % movey)))))))

(defn step
  []
  (doseq [enemy (keys @gl/enemies)]
    (move-enemy enemy)))


(defn spawn
  []
  (doseq [spawner @gl/spawners]
    (let [ac (:active-cooldown spawner)]
      (when (realized? @ac)
        (let [x (:x spawner)
              y (:y spawner)
              enemy (rand-nth (:enemies spawner))
              dir (rand-nth (:directions spawner))
              cooldown (:cooldown spawner)]
          (swap! gl/enemies
                 conj
                 {(ref (conj enemy {:x x :y y})) :on})
          (reset! ac (future (Thread/sleep cooldown))))))))

(defn respawn
  []
  (let [cooldown (atom (future (true)))]
    (if (realized? @cooldown)
      (do
        (spawn)
        (reset! cooldown (future (Thread/sleep 1000)))))))

(defn play
  []
  (loop [painter (atom (future (true)))]
    (spawn)
    (step)
    (when (realized? @painter)
      (reset! painter (future (redisplay gl/main-frame))))
    (Thread/sleep 3)
    (recur painter)))
