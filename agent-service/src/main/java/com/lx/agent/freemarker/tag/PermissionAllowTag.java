package com.lx.agent.freemarker.tag;

import com.core.utils.StringUtils;
import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @version createTime：Jun 3, 2013 2:29:28 PM
 */
public class PermissionAllowTag implements TemplateDirectiveModel {

    public static final String PERMISSIONVALUEKEY = "permission";
    private static final String PERMISSIONALLOWKEY = "allow";
    private static final String PERMISSIONIDKEY = "id";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String permissionAllow = getStringParam(params, PERMISSIONALLOWKEY);
        String permissionId = getStringParam(params, PERMISSIONIDKEY);

        String userPermission = String.valueOf(env.getDataModel().get(PERMISSIONVALUEKEY));

        if (!StringUtils.hasText(userPermission)) {
            return;
        }

        if ((StringUtils.hasText(permissionAllow) && userPermission.contains(permissionAllow)) || (StringUtils.hasText(permissionId) && userPermission.contains(permissionId))) {
            Writer output = env.getOut();
            body.render(output);
        }
    }

    String getStringParam(Map params, String key) throws TemplateModelException {
        Object value = params.get(key);
        if (value instanceof SimpleScalar) {
            return ((SimpleScalar) value).getAsString();
        }
        return null;
    }

}
