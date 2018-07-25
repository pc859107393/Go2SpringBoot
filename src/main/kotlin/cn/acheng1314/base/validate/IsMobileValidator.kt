package cn.acheng1314.base.validate

import cn.acheng1314.base.utils.StringUtil
import org.apache.commons.lang3.StringUtils
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext


class IsMobileValidator : ConstraintValidator<IsMobile, String> {

    private var required = false

    override fun initialize(isMobile: IsMobile?) {
        required = isMobile!!.required
    }

    override fun isValid(value: String, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return if (required) {
            StringUtil.isPhone(value)
        } else {
            if (StringUtils.isEmpty(value)) {
                true
            } else {
                StringUtil.isPhone(value)
            }
        }
    }
}
