void pattern(Line line, Line other) {
  Vector3D v1D = line.intersection(other);

    line.toSubSpace(v1D);
    other.toSubSpace(v1D);
  
}

