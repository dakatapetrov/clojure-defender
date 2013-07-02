(ns clojure-defender.levels.level-01)

(require '[clojure-defender.globals :as gl])

(def paths
  (for [x (range 0 700 30)
        y [100]]
    {:x x :y y :direction :right}))

(def protect-points [{:x 690 :y 100 :capacity 50}])

(def build-areas
  (for [x (range 0 700 90)
        y [70 130]]
    {:x x :y y}))

(def generators)
(def buildings)
(def enemies)
(def projectiles)

(defn load-paths
  []
  (doseq [path paths]
    (swap! gl/paths conj path)))

(defn load-protect-points
  []
  (doseq [protect-point protect-points]
    (swap! gl/protect-points conj protect-point)))

(defn load-build-areas
  []
  (doseq [build-area build-areas]
    (swap! gl/build-areas conj build-area)))

(defn load-generators [])
(defn load-buildings [])
(defn load-enemies [])
(defn load-projectiles [])

(defn load-level
  []
  (load-paths)
  (load-protect-points)
  (load-build-areas)
  (load-generators)
  (load-buildings)
  (load-enemies)
  (load-projectiles))
