package cn.bugstack.springframework.beans.factory;

import cn.bugstack.springframework.beans.BeansException;

/**
 * Interface to be implemented by beans that wish to be aware of their
 * owning {@link BeanFactory}.
 *
 * 实现此接口，既能感知到所属的 BeanFactory
 *
 * 作者：DerekYRC https://github.com/DerekYRC/mini-spring
 * @description 实现此接口，既能感知到所属的 BeanFactory
 * @date 2022/3/11
 *  /CodeDesignTutorials
 *
 */
public interface BeanFactoryAware extends Aware {

   void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
