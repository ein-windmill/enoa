/*
 * Copyright (c) 2018, enoa (fewensa@enoa.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.enoa.trydb.page;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

  private int pageNumber;
  private int pageSize;
  private int totalPage;
  private long offset;
  private long totalRow;
  private List<T> rows;

  public Page() {
  }

  public Page(int pageNumber, int pageSize, int totalPage, long offset, long totalRow, List<T> rows) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.totalPage = totalPage;
    this.totalRow = totalRow;
    this.rows = rows;
    this.offset = offset;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public long getTotalRow() {
    return totalRow;
  }

  public void setTotalRow(long totalRow) {
    this.totalRow = totalRow;
  }

  public List<T> getRows() {
    return rows;
  }

  public void setRows(List<T> rows) {
    this.rows = rows;
  }

  public long getOffset() {
    return offset;
  }

  public void setOffset(long offset) {
    this.offset = offset;
  }

  @Override
  public String toString() {
    return "Page{" +
      "pageNumber=" + pageNumber +
      ", pageSize=" + pageSize +
      ", totalPage=" + totalPage +
      ", offset=" + offset +
      ", totalRow=" + totalRow +
      ", rows=" + rows +
      '}';
  }
}
