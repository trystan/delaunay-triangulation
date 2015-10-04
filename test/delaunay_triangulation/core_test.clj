(ns delaunay-triangulation.core-test
  (:require [clojure.test :refer :all]
            [delaunay-triangulation.core :refer :all]))


(deftest circumscribe-triangle-test
  (testing "valid triangles"
    (is (= {:x 4 :y 2 :radius-squared 20.0}
           (circumscribe-triangle [[0 0] [8 0] [0 4]]))))

  (testing "invalid triangles"
    (is (= nil
           (circumscribe-triangle [[0 0] [8 0] [12 0]])))))


(deftest edges-test
  (is (= [[[0 0] [1 0]] [[1 0] [0 1]] [[0 1] [0 0]]]
         (edges [[0 0] [1 0] [0 1]]))))


(deftest contains-point?-test
  (testing "contains-point? inside circle"
    (is (= true (contains-point? {:x 4 :y 2 :radius-squared 20.0} [3 3]))))

  (testing "contains-point? outside circle"
    (is (= false (contains-point? {:x 4 :y 2 :radius-squared 20.0} [30 30]))))

  (testing "contains-point? on circle"
    (is (= false (contains-point? {:x 4 :y 2 :radius-squared 20.0} [24 0])))))


(deftest outer-edges-test
  (is (= [[[1 0] [0 -1]] [[0 -1] [-1 -1]] [[-1 -1] [-1 0]] [[-1 0] [1 0]]]
         (outer-edges [[[0 0] [1 0] [0 -1]]
                       [[0 0] [0 -1] [-1 -1]]
                       [[0 0] [-1 -1] [-1 0]]
                       [[0 0] [-1 0] [1 0]]]))))


(deftest make-new-triangles-test
  (is (= #{[[-5 5] [-5 -5] [0 1]]
           [[5 5] [5 -5] [0 1]]
           [[-5 -5] [5 -5] [0 1]]
           [[-5 5] [5 5] [0 1]]}
         (make-new-triangles [[[-5 -5] [5 -5] [-5 5]] [[5 -5] [-5 5] [5 5]]]
                             [0 1]))))


(deftest add-point-to-triangles-test
  (is (= #{[[-50 -50] [-55 -50] [-50 -55]]
           [[-5 5] [-5 -5] [0 1]]
           [[5 5] [5 -5] [0 1]]
           [[-5 -5] [5 -5] [0 1]]
           [[-5 5] [5 5] [0 1]]}
         (add-point-to-triangles #{[[-50 -50] [-55 -50] [-50 -55]] [[-5 -5] [5 -5] [-5 5]] [[5 -5] [-5 5] [5 5]]}
                                 [0 1]))))


(deftest bounds-test
  (is (= [[-1010 1010] [1400 1010] [-1010 -1020] [1400 -1020]]
         (bounds [[-10 10] [0 0] [400 -20]]))))


(deftest triangulate-test
  (testing "with valid points"
    (is (= {:points [[-10.0 10.0] [0.0 5.0] [10.0 20.0]]
            :triangles [[[0.0 5.0] [-10.0 10.0] [10.0 20.0]]]
            :edges [[[0.0 5.0] [-10.0 10.0]] [[-10.0 10.0] [10.0 20.0]] [[10.0 20.0] [0.0 5.0]]]}
           (triangulate [[-10 10] [0 5] [10 20]]))))

  (testing "with colinier points"
    (is (= {:points [[-10.0 10.0] [0.0 10.0] [10.0 10.0]],
            :triangles [],
            :edges []}
           (triangulate [[-10 10] [0 10] [10 10]])))))
