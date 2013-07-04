(ns clojure-defender.levels.level-01)

(require '[clojure-defender.globals :as gl])

(def paths
  (vec (concat (for [x (range 0 700 30)
                      y [100 490]]
                  {:x x :y y :direction [1 0]})
                [{:x 720 :y 100 :direction [0.5 1]}]
                (for [x [720]
                      y (range 130 500 30)]
                  {:x x :y y :direction [0 1]}))))

(def defend-points [{:x 720 :y 490 :capacity 50}])

(def build-areas
  (for [x (range 0 700 90)
        y [70 130]]
    {:x x :y y}))

(def spawners
  [{:x 0 :y 110 :enemies [{:speed 0.1} {:speed 0.3}]
    :cooldown 1000 :active-cooldown (atom (future (true)))}
   {:x 0 :y 500 :enemies [{:speed 0.1} {:speed 0.3}]
    :cooldown 3000 :active-cooldown (atom (future (true)))}])

(def buildings)
(def enemies)
(def projectiles)

(defn load-paths
  []
  (doseq [path paths]
    (swap! gl/paths conj path)))

(defn load-defend-points
  []
  (doseq [protect-point defend-points]
    (swap! gl/defend-points conj protect-point)))

(defn load-build-areas
  []
  (doseq [build-area build-areas]
    (swap! gl/build-areas conj build-area)))

(defn load-spawners
  []
  (doseq [spawner spawners]
    (swap! gl/spawners conj spawner)))

(defn load-buildings [])
(defn load-enemies [])
(defn load-projectiles [])

(defn load-level
  []
  (load-paths)
  (load-defend-points)
  (load-build-areas)
  (load-spawners)
  (load-buildings)
  (load-enemies)
  (load-projectiles))
