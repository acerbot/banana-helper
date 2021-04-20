package com.zhuweitung.task;

import com.zhuweitung.push.PushHelper;
import com.zhuweitung.push.model.PushMetaInfo;
import com.zhuweitung.signin.ServerVerify;
import com.zhuweitung.util.ConfigLoadUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

/**
 * 消息发送统一入口
 * @author zhuweitung
 * @create 2021/4/20
 */
@Log4j2
public class ServerPush {

    public static void doServerPush() {
        PushMetaInfo.PushMetaInfoBuilder builder = PushMetaInfo.builder().numberOfRetries(3);
        PushHelper.Target target = null;
        if (StringUtils.isNotBlank(ServerVerify.getFtkey())) {
            builder = builder.token(ServerVerify.getFtkey());
            if (ServerVerify.getFtkey().startsWith("SCT")) {
                target = PushHelper.Target.SERVER_CHAN_TURBO;
                log.info("本次执行推送日志到Server酱Turbo版本");
            }
        } else {
            log.info("未配置server酱,本次执行不推送日志到微信");
        }
        if (null != target) {
            PushHelper.push(target, builder.build(), ConfigLoadUtil.loadFile("logs/daily.log"));
        }
    }
}
