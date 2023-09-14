import java.util.*;

class Circular {

    static class Unit {
      private String data;
      private String dependsOn;

      public Unit(String data, String dependsOn) {
        this.data = data;
        this.dependsOn = dependsOn;
      }

      public void print() {
        System.out.printf("[%s, %s]\n", this.data, this.dependsOn);
      }
    }


    static int find(LinkedList<String> list, String ele) {
      if (ele == null) return -2; // Means it does not depend on anyone
      for (int i = 0; i < list.size(); i++) {
        if (list.get(i) == ele) return i;
      }
      return -1; // This element does not exist
    }

    static boolean isCircular(Unit[] units) {
      if (units == null || units.length == 0)  return false;
      if (units.length == 1) return units[0].data == units[0].dependsOn;

      // We will populate the data with dependencies in a singly linked list
      // and we will check if any elemnet is coming twice in the list.
      LinkedList<String> list = new LinkedList<String>();
      for (int i = 0; i < units.length; i++) {
        // First element
        if (list.size() == 0) {
          if (units[0].data != null) list.add(units[0].data);
          if (units[0].dependsOn != null && units[0].data != null) list.add(0, units[0].dependsOn);
          continue;
        }
        // Check if the element exists
        int index = find(list, units[i].dependsOn);
        if (index == -2) { // If the element does not depend on any
          if (units[i].data != null) {
            int index2 = find(list, units[i].data);
            if (index2 >= 0) continue; // If already present, skip
            else list.add(units[i].data);
          }
        } else if (index == -1) { // If the dependency is not found
          int index2 = find(list, units[i].data);
          if (index2 == -2) continue;
          else if (index2 == -1) { // Data is also not found, then add both
            list.add(0, units[i].dependsOn);
            if (units[i].data != null) list.add(1, units[i].data);
          } else {
            list.add(index2, units[i].dependsOn);
          } 
        } else { // Found the location of dependsOn
          int index2 = find(list, units[i].data);
          if (index2 < index) {
            if (units[i].data != null) list.add(index, units[i].data);
          }
        }
      }  
      System.out.println(list);
      return hasDuplicate(list);
    }
    
    static boolean hasDuplicate(LinkedList<String> list) {
      HashSet<String> set = new HashSet<String>();
      for (int i = 0; i < list.size(); i++) {
        set.add(list.get(i));
      }
      return set.size() < list.size();
    }

    static void test01() {
      Unit[] units = {
        new Unit("a", null),
        new Unit("b", null)
      };      
      boolean res = isCircular(units);
      System.out.println("IsCircular = " + res);
      System.out.println("-------------------");
      //[a, b]
      //IsCircular = false
    }
    
    static void test02() {
      Unit[] units = {
        new Unit("a", "b"),
        new Unit("b", "c")
      };      
      boolean res = isCircular(units);
      System.out.println("IsCircular = " + res);
      System.out.println("-------------------");
      //[c, b, a]
      //IsCircular = false
    }

    static void test03() {
      Unit[] units = {
        new Unit("a", "b"),
        new Unit("b", "c"),
        new Unit("a", "d")
      };      
      boolean res = isCircular(units);
      System.out.println("IsCircular = " + res);
      System.out.println("-------------------");
      //[c, b, d, a]
      //IsCircular = false
    }

    static void test04() {
      Unit[] units = {
        new Unit("a", "b"),
        new Unit("b", "c"),
        new Unit("a", "d"),
        new Unit("b", "d")
      };
      boolean res = isCircular(units);
      System.out.println("IsCircular = " + res);
      System.out.println("-------------------");
      //[c, b, b, d, a]
      //IsCircular = true
    }

    static void test05() {
      Unit[] units = {
        new Unit("a", "b"),
        new Unit("b", "c"),
        new Unit("c", "d"),
        new Unit("d", "a")
      };
      boolean res = isCircular(units);
      System.out.println("IsCircular = " + res);
      System.out.println("-------------------");
      //[d, c, b, d, a]
      //IsCircular = true
    }

    static void test06() {
      Unit[] units = {
        new Unit("a", null),
        new Unit("a", "b"),
        new Unit("b", "c"),
        new Unit("a", "b")
      };
      boolean res = isCircular(units);
      System.out.println("IsCircular = " + res);
      System.out.println("-------------------");
      //[c, b, a]
      //IsCircular = false
    }

    static void test07() {
      Unit[] units = {
        new Unit("a", null),
        new Unit(null, "b")
      };
      boolean res = isCircular(units);
      System.out.println("IsCircular = " + res);
      System.out.println("-------------------");
      //[a]
      //IsCircular = false
    }

   static void test08() {
      Unit[] units = {
        new Unit("a", null),
        new Unit(null, "a")
      };
      boolean res = isCircular(units);
      System.out.println("IsCircular = " + res);
      System.out.println("-------------------");
      //[a]
      //IsCircular = false
    }

    static void test09() {
      Unit[] units = {
        new Unit(null, "a"),
        new Unit(null, "b")
      };
      boolean res = isCircular(units);
      System.out.println("IsCircular = " + res);
      System.out.println("-------------------");
      //[]
      //IsCircular = false
    }

    public static void main(String[] args) {
      test01();
      test02();
      test03();
      test04();
      test05();
      test06();
      test07();
      test08();
      test09();
    }
}
