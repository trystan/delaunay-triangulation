# delaunay-triangulation

Get a delaunay triangulation for a given collection of points.

## Leiningen

[trystan/delaunay-triangulation "1.0.1"]

## Usage

There's only one usefull function, `triangulate`. It takes a collection of points and returns a map with :points, :edges, and :triangles. Each point is a vector of two coordinates, each edges is a vector of two points, and each triangle is a vector of three points.

    (ns example.core
      (:require [delaunay-triangulation.core :as delaunay]))

    (let [points [[2 3] [-4 9.3] [4 3] [2 2]]
          {:keys [points edges triangles]} (delaunay/triangulate points)]
      (println "point" (first points))
      (println "edge" (first edges))
      (println "triangle" (first triangles)))

    #=> point [2.0 3.0]
    #=> edge [[2.0 3.0] [4.0 3.0]]
    #=> triangle [[2.0 3.0] [4.0 3.0] [2.0 2.0]]


## License

Copyright © 2015 Trystan

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
