package cn.acheng1314.base.config

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.interceptor.*
import java.util.*

@Configuration
@EnableTransactionManagement
class TransactionConfig {

    fun transactionAttributeSource(): TransactionAttributeSource {
        val source = NameMatchTransactionAttributeSource()
        //只读或可写事物
        val readOnlyTx = RuleBasedTransactionAttribute()
        readOnlyTx.isReadOnly = true
        readOnlyTx.propagationBehavior = TransactionDefinition.PROPAGATION_SUPPORTS

        //可写事物
        val requiredTx = RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED
                , Collections.singletonList(RollbackRuleAttribute(Exception::class.java)))
        //使用kotlin作用域函数
        return source.also { it ->
            it.setNameMap(HashMap<String, TransactionAttribute>().also {
                it["add*"] = requiredTx
                it["save*"] = requiredTx
                it["insert*"] = requiredTx
                it["update*"] = requiredTx
                it["delete*"] = requiredTx
                it["get*"] = readOnlyTx
                it["query*"] = readOnlyTx
                it["find*"] = readOnlyTx
            })
        }
    }

    /*事务拦截器*/
    @Bean(value = ["txInterceptor"])
    fun getTransactionInterceptor(tx: PlatformTransactionManager): TransactionInterceptor {
        return TransactionInterceptor(tx, transactionAttributeSource())
    }

    /**切面拦截规则 参数会自动从容器中注入 */
    @Bean
    fun pointcutAdvisor(txInterceptor: TransactionInterceptor): AspectJExpressionPointcutAdvisor {
        val pointcutAdvisor = AspectJExpressionPointcutAdvisor()
        pointcutAdvisor.advice = txInterceptor
        pointcutAdvisor.expression = "execution (* cn.acheng1314.base.service.*ServiceImpl.*(..))"
        return pointcutAdvisor
    }
}