package com.proj.itstaym.libsyst.controller.records;

import java.math.BigInteger;

public record BookRecord (BigInteger id, String title, String author, String edition, String publisher, Integer year, String code, Integer copies) { }
