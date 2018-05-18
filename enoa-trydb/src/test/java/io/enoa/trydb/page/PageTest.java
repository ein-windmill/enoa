package io.enoa.trydb.page;

import ikidou.reflect.TypeBuilder;
import io.enoa.json.EMgrJson;
import io.enoa.json.kit.JsonKit;
import io.enoa.json.provider.fastjson.FastjsonProvider;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PageTest {

  @Before
  public void before() {
    EMgrJson.instance().defJsonFactory(new FastjsonProvider());
  }


  @Test
  public void testPage() {
    Page<E0> page = new Page<>(1, 10, 10, 0L, 100L, new ArrayList<E0>() {{
      add(new E0().setId(0).setName("a"));
      add(new E0().setId(1).setName("b"));
      add(new E0().setId(2).setName("c"));
      add(new E0().setId(3).setName("d"));
      add(new E0().setId(4).setName("e"));
      add(new E0().setId(5).setName("f"));
      add(new E0().setId(6).setName("g"));
      add(new E0().setId(7).setName("h"));
      add(new E0().setId(8).setName("i"));
      add(new E0().setId(9).setName("j"));
    }});

    String data = JsonKit.toJson(page);
    System.out.println(data);

    Page<Integer> p1 = JsonKit.parse(data, TypeBuilder.newInstance(Page.class).addTypeParam(E0.class).build());
    System.out.println(p1);

  }


  public static class E0 {
    private Integer id;
    private String name;

    public Integer getId() {
      return id;
    }

    public E0 setId(Integer id) {
      this.id = id;
      return this;
    }

    public String getName() {
      return name;
    }

    public E0 setName(String name) {
      this.name = name;
      return this;
    }

    @Override
    public String toString() {
      return "E0{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
    }
  }

}
