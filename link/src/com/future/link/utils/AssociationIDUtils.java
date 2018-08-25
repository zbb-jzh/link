package  com.future.link.utils;

/**
 * /**
 * 关联id列表，这里推荐每个等级id的位数是2，3，4，6这几个位数
 * Created by zhengbingbing on 2016/3/11.
 */
public class AssociationIDUtils {

    /**
     * 返回id的级别数，范围从1开始，如果返回0代表数据不合法
     * @param id
     * @param stepSize 每级十进制占位数
     * @return
     */
    public static int getLevel(long id, int stepSize){
        if(stepSize<= 0) {
            return 0;
        }
        if(id< scope[stepSize][0] || id>scope[stepSize][1]) {
            return 0;
        }
        int i = 0;
        for(; i <levelIdStartArray[stepSize].length; i++) {
            if(id% levelIdStartArray[stepSize][i]== 0) {
                break;
            }
        }

        return i + 1;
    }

    /**
     * 根据给定的id，得到该id指定级别的值
     * @return
     */
    public static long getLevelValue(long id, int level, int stepSize){
        if(stepSize<= 0) {
            return 0;
        }
        if(id< scope[stepSize][0] || id>scope[stepSize][1]) {
            return 0;
        }

        long rst= 0;
        long stuffix= levelIdStartArray[stepSize][level-1];
        rst= id/ stuffix * stuffix;
        return rst;
    }

    /**
     * 得到当前id的子id范围(主要用户数据库查询子id最大值，然后+1得到当前id)
     * @param id
     * @param stepSize 每级十进制占位数
     * @return [0]下边界，[1]上边界,[2]id的级别(数字1~(x-1))，如果数据不合法返回null
     */
    public static long[] getChildrenScope(long id, int stepSize) {
        if(stepSize<= 0) {
            return null;
        }
        if(id< scope[stepSize][0] || id>scope[stepSize][1]) {
            return null;
        }

        long[] rst= new long[3];

        int i=getLevel(id, stepSize);
        rst[2]= i;
        if(i==0) {		//如果是错误，或者最后一级(第六级)，则没有子级
            return null;
        }

        /**
         * 如果是最后一级的id，已经没有子级，则上限和下限都是自己
         */
        if(i== levelIdStartArray[stepSize].length+ 1) {
            rst[0]= id;
            rst[1]= id;
        } else {		//计算子节点
            long nextId = getNextId(id, stepSize);
            rst[0]= id+ 1;
            rst[1]= nextId -1;

//			i= i-1;
//			long prefix = id;
//			if(i< levelIdStartArray[stepSize].length) {
//				prefix= id / levelIdStartArray[stepSize][i];		//父级别的数字
//				prefix= prefix* SETP[stepSize];		//转换出子级别数字
//				rst[0] =prefix+ 1;
//				rst[1] = prefix+ 999;
//				/**
//				 * 如果后面需要补0
//				 */
//				int stuffixZero= levelIdStartArray[stepSize].length- 1;
//				if(stuffixZero-i!= 0) { 	//4= levelArray.length- 1;
//					long stuffix = (long) Math.pow(SETP[stepSize], (stuffixZero-i));
//					rst[0] = rst[0]* stuffix;
//					rst[1] = rst[1]* stuffix;
//				}
//			}
        }


        return rst;
    }

    /**
     * 得到当前id的指定level级别的下一个id
     * @param id 原始id
     * @param level 指定级别
     * @param stepSize 每级十进制占位数
     * @return 返回指定级别的下一个id，0=错误可能有id或者level不合法
     */
    public static long getNextId4Level(Long id, int level, int stepSize) {
        if(id== null) {
            if(level==1) {	//如果是初始化第一个id
                return levelIdStartArray[stepSize][0];
            } else {
                return 0;
            }
        }
        if(stepSize<= 0) {
            return 0;
        }
        if(id< scope[stepSize][0] || id>scope[stepSize][1]) {
            return 0;
        }
        if(level<1 || level>LEVEL_SIZE[stepSize]) {
            return 0;
        }

        long rst = id;
        int index = level- 1;
        if(index< levelIdStartArray[stepSize].length) {
            rst = rst / levelIdStartArray[stepSize][index];
            rst+= 1;
            rst*= levelIdStartArray[stepSize][index];
        } else {
            rst+= 1;
        }

        return rst;
    }

    /**
     * 得到当前id的所属level级别的下一个id
     * @param id 原始id
     * @param stepSize 每级十进制占位数
     * @param stepSize 每级十进制占位数
     * @return 返回指定级别的下一个id，0=错误
     */
    public static long getNextId(long id, int stepSize) {
        if(stepSize<= 0) {
            return 0;
        }
        if(id< scope[stepSize][0] || id>scope[stepSize][1]) {
            return 0;
        }
        int level = getLevel(id, stepSize);
        if(level<1 || level>6) {
            return 0;
        }
        long rst = id;
        int index = level- 1;
        if(index< levelIdStartArray[stepSize].length) {
            rst = rst / levelIdStartArray[stepSize][index];
            rst+= 1;
            rst*= levelIdStartArray[stepSize][index];
        } else {
            rst+= 1;
        }

        return rst;
    }

    /**
     * 数组第一级是位数，
     * 第二级数组表示该级别数字(此时下标从0开始)的第一位数字，比如步长3的id的第一位应该是从第16为开始的那么为1000000000000000
     */
    private static final long[][] levelIdStartArray ={
            {0 } ,
            {100000000000000000L, 10000000000000000L, 1000000000000000L, 100000000000000L, 10000000000000L, 1000000000000L, 100000000000L, 10000000000L, 1000000000, 100000000, 10000000, 1000000, 100000, 10000, 1000, 100, 10 } ,
            {10000000000000000L, 100000000000000L, 1000000000000L, 10000000000L, 100000000, 1000000, 10000, 100},
            {1000000000000000L, 1000000000000L, 1000000000L, 1000000L, 1000L } ,
            {1000000000000L, 100000000, 10000} ,
            {10000000000L, 100000 } ,
            {1000000000000L, 1000000L } ,
            {10000000L } ,
            {100000000L } ,
            {1000000000L }
    };

    /**
     * 每种步长有多少级
     */
    private static final int[] LEVEL_SIZE = {0, 18, 9, 6, 4, 3, 3, 2, 2, 2};

    /**
     * 每种步长对应的数的范围
     */
    private static final long[][] scope = {
            {0, 0},
            {100000000000000000L, 999999999999999999L},
            {10000000000000000L, 999999999999999999L},
            {1000000000000000L, 999999999999999999L},
            {1000000000000L, 9999999999999999L},
            {10000000000L, 999999999999999L},
            {1000000000000L, 999999999999999999L},
            {10000000L, 99999999999999L},
            {100000000L, 9999999999999999L} ,
            {1000000000L, 999999999999999999L}
    };

    /**
     * 每种长度对应的步长单位
     */
    private static final long SETP[] ={
            0,
            10,
            100,
            1000,
            10000,
            100000,
            1000000,
            10000000,
            100000000,
            1000000000,
    };

    public static void main(String[] args) {
//		System.out.println(getLevel(1000000000000000L));

//		测试4位
//		System.out.println(getLevel(1000000000000L, 4));
//		System.out.println(getNextId4Level(1000000000000L, 1, 4));
//		System.out.println(getNextId4Level(1000000000000L, 1, 4));
//		System.out.println(getNextId4Level(1000000000000L, 3, 4));
//		System.out.println(getNextId(1000000000000L, 4));
//		System.out.println(getChildrenScope(1000000000000L, 4));
////		System.out.println(getLevel(1000000000000000L, 3));
////
////		测试3位
//		System.out.println(getNextId4Level(1000010000000050L, 1, 3));
//		System.out.println(getNextId4Level(100000010000000050L, 1, 3));
//		System.out.println(getNextId4Level(100000010000000050L, 3, 3));
//		System.out.println(getNextId4Level(100000010000000050L, 6, 3));
//		System.out.println(getChildrenScope(100000010000000050L, 3));

        System.out.println(getLevelValue(16099100000000L, 1, 5));


//		int levelSize = 2;
//		for(int i=1;i<levelSize; i++ ) {
//			System.out.print(((long)Math.pow(1000000000, levelSize- i))+ "L, ");
//		}

//		getChildrenScope(1000000000000000L);
//		getChildrenScope(1000000010000000L);
//
//		getChildrenScope(100000010000000000L);
//		getChildrenScope(100000000000001000L);
    }
}
