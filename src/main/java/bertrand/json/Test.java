package bertrand.json;

import java.util.List;


public class Test {
    public Double code;
    public String msg;
    public Data data;

    public static class Data {
        public String name;
        public String avatar;
        public String expenseSum;
        public List<ExpenseDetail> expenseDetail;

        public static class ExpenseDetail {
            public Double month;
            public Double expense;
        }
    }
}
