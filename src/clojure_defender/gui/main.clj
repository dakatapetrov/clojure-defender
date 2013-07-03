(ns clojure-defender.gui.main)

(use 'clojure.repl)
(use '[seesaw core color graphics behave keymap])
(require '[clojure-defender.globals :as gl])
(use 'clojure-defender.levels.level-01)

(defn display [fr content]
  (config! fr :content content)
  content)

(defn draw-play-area
  [g]
  (draw g (rect 0 0 gl/window-width gl/window-height) (style :background "#3eb677")))


(defn draw-paths
  [g]
  (doseq [path @gl/paths]
    (let [x (:x path)
          y (:y path)]
      (draw g (rect x y gl/path-size) (style :background "#e39e54")))))

(defn draw-protect-points
  [g]
  (doseq [protect-point @gl/protect-points]
    (let [x (:x protect-point)
          y (:y protect-point)]
      (draw g (rect x y gl/path-size) (style :background :red)))))

(defn draw-build-areas
  [g]
  (doseq [build-area @gl/build-areas]
    (let [x (:x build-area)
          y (:y build-area)]
      (draw g (rect x y gl/building-size) (style :background "#f4dd65")))))

(defn draw-enemies
  [g]
  (doseq [enemy (keys @gl/enemies)]
    (let [x (:x @enemy)
          y (:y @enemy)]
      (draw g (rect x y gl/enemy-size) (style :background :blue)))))


(defn draw-world
  [c g]
  (draw-play-area g)
  (draw-paths g)
  (draw-protect-points g)
  (draw-build-areas g)
  (draw-enemies g))
  ; (draw g (polygon [0 0] [30 0] [30 30] [0 30]) (style :background "#464248"))
  ; (draw g (polygon [10 10] [20 10] [20 20] [10 20]) (style :background :red)))

(defn make-panel []
  (border-panel
    :center (canvas :paint draw-world
              :class :world
              :background :black)))

(defn redisplay [root]
  (dosync
    (config! (select root [:.world]) :paint draw-world)))

(defn create-gui
  []
  (-> gl/main-frame show!)
  (display gl/main-frame (make-panel)))
