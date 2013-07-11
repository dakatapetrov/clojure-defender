(ns clojure-defender.levels.level-01)

(require '[clojure-defender.globals :as gl])
(require '[clojure-defender.data.enemies :as ed])

(def lives 20)

(def funds 200)

(def world {:x 0 :y 0 :width 980 :height 768 :color "#3eb677"})

(def paths
  (vec (concat (for [x (range 0 700 30)
                      y [100 490]]
                  {:x x :y y :direction [1 0] :color "#e39e54"})
                [{:x 720 :y 100 :direction [0.5 1] :color "#e39e54"}]
                (for [x [720]
                      y (range 130 500 30)]
                  {:x x :y y :direction [0 1] :color "#e39e54"}))))

(def defend-points [{:x 720 :y 490 :capacity 50 :color :red}])

(def build-areas
  (for [x (range 0 700 90)
        y [70 130]]
    {:x x :y y :color "#f4dd65"}))

(def spawners
  [{:x 0 :y 110 :enemies [ed/assassin ed/zombie ed/orc]
    :cooldown 600 :ac (atom 0)}
   {:x 0 :y 500 :enemies [ed/assassin ed/zombie]
    :cooldown 2000 :ac (atom 0)}])

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

(defn load-lives
  []
  (reset! gl/lives lives))

(defn load-funds
  []
  (reset! gl/funds funds))

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
  (load-projectiles)
  (load-lives)
  (load-funds))
