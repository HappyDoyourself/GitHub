package cn.com.jiuyao.constant;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by Larry on 2016/1/12.
 * 壹钱包商品分类
 */
public class GoodsCatalog {

    public enum Catalog {
        //OTC药品,参茸中药 处方药     D01	药品
        //滋补保健 	                DO2	保健品
        //器械线,计生用品,隐形眼镜	    DO3	器械
        //家居日化，小家电,其他，美妆护理 	D06	其他
        OTC(13), SRZY(15), CFY(24), ZBBJ(16), QXX(14), JSYP(19), YXYJ(21);

        private final int catalogId;

        Catalog(int catalogId) {
            this.catalogId = catalogId;
        }

        public int getCatalogId() {
            return catalogId;
        }
    }

    /**
     * D01药品集合，包含OTC药品,参茸中药 处方药
     *
     * @return set
     */
    public static Set<Catalog> d01() {
        return EnumSet.of(Catalog.OTC, Catalog.SRZY, Catalog.CFY);
    }

    /**
     * D02保健品集合，包含滋补保健
     *
     * @return set
     */
    public static Set<Catalog> d02() {
        return EnumSet.of(Catalog.ZBBJ);
    }

    /**
     * D03器械集合，包含器械线,计生用品,隐形眼镜
     *
     * @return set
     */
    public static Set<Catalog> d03() {
        return EnumSet.of(Catalog.QXX, Catalog.JSYP, Catalog.YXYJ);
    }

    /**
     * 遍历集合，集合中是否存在cataId为i的元素
     * @param set
     * @param i
     * @return
     */
    public static boolean isHas(Set<Catalog> set , int i){
        for (Catalog catalog : set) {
            if (catalog.getCatalogId() == i) {
                return true;
            }
        }
        return false;
    }
}
