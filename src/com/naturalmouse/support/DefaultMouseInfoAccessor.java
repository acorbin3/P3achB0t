package com.naturalmouse.support;

import com.naturalmouse.api.MouseInfoAccessor;

import java.awt.*;

public class DefaultMouseInfoAccessor implements MouseInfoAccessor {

  @Override
  public Point getMousePosition() {
    return MouseInfo.getPointerInfo().getLocation();
  }
}
