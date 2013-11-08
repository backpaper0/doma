/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.doma.internal.apt.meta;

import static org.seasar.doma.internal.util.AssertionUtil.assertNotNull;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

import org.seasar.doma.Delegate;
import org.seasar.doma.internal.apt.AptIllegalStateException;

/**
 * @author taedium
 * 
 */
public class DefaultQueryMetaFactory extends
        AbstractQueryMetaFactory<DefaultQueryMeta> {

    public DefaultQueryMetaFactory(ProcessingEnvironment env) {
        super(env);
    }

    @Override
    public QueryMeta createQueryMeta(ExecutableElement method, DaoMeta daoMeta) {
        assertNotNull(method, daoMeta);
        /*
         * TODO
         * 
         * eclipse JDT 1.0.0.v20131108-0105_BETA_JAVA8 has not yet supported a
         * 'isDefault' method.
         */
        // if (!method.isDefault()) {
        // return null;
        // }
        // DefaultQueryMeta queryMeta = new DefaultQueryMeta(method);
        // queryMeta.setQueryKind(QueryKind.DEFAULT);
        // doTypeParameters(queryMeta, method, daoMeta);
        // doParameters(queryMeta, method, daoMeta);
        // doReturnType(queryMeta, method, daoMeta);
        // doThrowTypes(queryMeta, method, daoMeta);
        // return queryMeta;
        return null;
    }

    protected TypeMirror getTargetType(Delegate delegate) {
        try {
            delegate.to();
        } catch (MirroredTypeException e) {
            return e.getTypeMirror();
        }
        throw new AptIllegalStateException("unreachable.");
    }

    @Override
    protected void doParameters(DefaultQueryMeta queryMeta,
            ExecutableElement method, DaoMeta daoMeta) {
        for (VariableElement parameter : method.getParameters()) {
            QueryParameterMeta parameterMeta = createParameterMeta(parameter);
            queryMeta.addParameterMeta(parameterMeta);
            if (parameterMeta.isBindable()) {
                queryMeta.addBindableParameterCtType(parameterMeta.getName(),
                        parameterMeta.getCtType());
            }
        }
    }

    @Override
    protected void doReturnType(DefaultQueryMeta queryMeta,
            ExecutableElement method, DaoMeta daoMeta) {
        QueryReturnMeta resultMeta = createReturnMeta(method);
        queryMeta.setReturnMeta(resultMeta);
    }

}