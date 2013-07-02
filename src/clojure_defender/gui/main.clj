(ns clojure-defender.gui.main)

(use 'clojure.repl)
(use '[seesaw core color graphics behave keymap])
(require '[clojure-defender.globals :as gl])
(use 'clojure-defender.levels.level-01)

(defn display [fr content]
  (config! fr :content content)
  content)

(defn draw-rectangle
  [g x y dx dy s]
  (draw g (polygon [x y] [(+ x dx) y] [(+ x dx) (+ y dy)] [x (+ y dy)]) s))

(defn draw-square
  [g x y d s]
  (draw-rectangle g x y d d s))

(defn draw-play-area
  [c g]
  (draw-rectangle g 0 0 800 600 (style :background "#3eb677")))


(defn draw-paths
  [c g]
  (doseq [path @gl/paths]
    (let [x (:x path)
          y (:y path)]
      (draw-square g x y 30 (style :background "#e39e54")))))

(defn draw-protect-points
  [c g]
  (doseq [protect-point @gl/protect-points]
    (let [x (:x protect-point)
          y (:y protect-point)]
      (draw-square g x y 30 (style :background :red)))))

(defn draw-build-areas
  [c g]
  (doseq [build-area @gl/build-areas]
    (let [x (:x build-area)
          y (:y build-area)]
      (draw-square g x y 30 (style :background "#f4dd65")))))


(defn draw-world
  [c g]
  (draw-play-area c g)
  (draw-paths c g)
  (draw-protect-points c g)
  (draw-build-areas c g))
  ; (draw g (polygon [0 0] [30 0] [30 30] [0 30]) (style :background "#464248"))
  ; (draw g (polygon [10 10] [20 10] [20 20] [10 20]) (style :background :red)))

(defn make-panel []
  (border-panel
    :center (canvas :paint draw-world
              :class :world
              :background :black)))

(defn create-gui
  []
  (-> gl/main-frame show!)
  (display gl/main-frame (make-panel)))
