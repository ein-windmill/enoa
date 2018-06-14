//package io.enoa.index.solr.promise;
//
//import io.enoa.index.solr.report.ISolrReporter;
//import io.enoa.promise.DoneArgPromise;
//import io.enoa.promise.arg.PromiseArg;
//import io.enoa.promise.arg.PromiseCapture;
//import io.enoa.promise.arg.PromiseVoid;
//import io.enoa.promise.builder.EGraenodPromiseBuilder;
//import io.enoa.promise.builder.PromiseBuilder;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Deprecated
//public class EReportGraenodPromiseBuilder<T> {
//
//  private EGraenodPromiseBuilder<T> egp;
//
//  public EReportGraenodPromiseBuilder() {
//    this.egp = PromiseBuilder.donearg();
//  }
//
//  private List<ISolrReporter> reporters;
//
//  public List<ISolrReporter> reporters() {
//    return this.reporters == null ? Collections.emptyList() : this.reporters;
//  }
//
//  public List<PromiseArg<T>> dones() {
//    return this.egp.dones();
//  }
//
//  public List<PromiseCapture> captures() {
//    return this.egp.captures();
//  }
//
//  public PromiseVoid always() {
//    return this.egp.always();
//  }
//
//  public ReporterDoneArgPromise<T> build() {
//    DoneArgPromise<T, DoneArgPromise> promise = this.egp.build();
//    return new ReporterDoneArgPromise<T>() {
//      @Override
//      public ReporterDoneArgPromise<T> report(ISolrReporter ret) {
//        if (reporters == null)
//          reporters = new ArrayList<>();
//        reporters.add(ret);
//        return this;
//      }
//
//      @Override
//      public ReporterDoneArgPromise<T> done(PromiseArg<T> done) {
//        promise.done(done);
//        return this;
//      }
//
//      @Override
//      public ReporterDoneArgPromise<T> capture(PromiseCapture capture) {
//        promise.capture(capture);
//        return this;
//      }
//
//      @Override
//      public void always(PromiseVoid always) {
//        promise.always(always);
//      }
//    };
//  }
//
//}
