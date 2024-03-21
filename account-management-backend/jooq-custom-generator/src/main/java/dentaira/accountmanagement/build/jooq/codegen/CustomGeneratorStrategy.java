package dentaira.accountmanagement.build.jooq.codegen;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

/**
 * JOOQのコード生成をカスタマイズするためのクラス
 * <p>
 * build.gradle で読み込んで使用する
 *
 * @see <a href="https://github.com/etiennestuder/gradle-jooq-plugin/tree/main/example/configure_custom_generator_strategy">参考にしたサンプル</a>
 */
public class CustomGeneratorStrategy extends DefaultGeneratorStrategy {

    /**
     * 全ファイルをrootパッケージに作成する
     *
     * <p>
     * Table や Record は Repository から参照するためにモジュールとして設定したいが、
     * デフォルトのパッケージ構成だと循環参照になっていて Spring Modulith に適さないためカスタマイズする。
     * 全ファイル一律同じパッケージに作成することで１つのモジュールにまとめることで、モジュール間の循環参照を解消する。
     */
    @Override
    public String getJavaPackageName(Definition definition, Mode mode) {
        return getTargetPackage();
    }
}
