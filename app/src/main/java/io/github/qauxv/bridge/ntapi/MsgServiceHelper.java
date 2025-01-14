/*
 * QAuxiliary - An Xposed module for QQ/TIM
 * Copyright (C) 2019-2023 QAuxiliary developers
 * https://github.com/cinit/QAuxiliary
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either
 * version 3 of the License, or any later version and our eula as published
 * by QAuxiliary contributors.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/cinit/QAuxiliary/blob/master/LICENSE.md>.
 */

package io.github.qauxv.bridge.ntapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cc.ioctl.util.Reflex;
import com.tencent.qqnt.kernel.nativeinterface.IKernelMsgService;
import io.github.qauxv.util.Initiator;
import java.lang.reflect.Method;
import mqq.app.AppRuntime;
import mqq.app.api.IRuntimeService;

public class MsgServiceHelper {

    private MsgServiceHelper() {
    }

    @NonNull
    public static Object getMsgService(@NonNull AppRuntime app) throws ReflectiveOperationException, LinkageError {
        // IMsgService msgService = ((IKernelService) app.getRuntimeService(IKernelService.class, "")).getMsgService();
        Class<? extends IRuntimeService> kIKernelService = (Class<? extends IRuntimeService>) Initiator.loadClass("com.tencent.qqnt.kernel.api.IKernelService");
        IRuntimeService kernelService = app.getRuntimeService(kIKernelService, "");
        Method getMsgService = kernelService.getClass().getMethod("getMsgService");
        return getMsgService.invoke(kernelService);
    }

    @Nullable
    public static IKernelMsgService getKernelMsgService(@NonNull AppRuntime app) throws ReflectiveOperationException, LinkageError {
        Object msgService = getMsgService(app);
        Method getKMsgSvc = Reflex.findSingleMethod(msgService.getClass(), IKernelMsgService.class, false);
        return (IKernelMsgService) getKMsgSvc.invoke(msgService);
    }

}
