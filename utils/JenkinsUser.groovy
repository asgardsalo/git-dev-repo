import hudson.model.Cause
import hudson.tasks.Mailer
import hudson.model.User

String getUsernameForBuild() {
    def causes = currentBuild.rawBuild.getCauses()
    for (Cause cause in causes) {
        def user
        if (cause instanceof hudson.model.Cause.userIdCause) {
            hudson.model.Cause.UseridCause userIdCause = cause
            user = userIdCause.getuserName()
            return user
        }
    }
    return null
}

String getUserIdForBuild() {
    def causes = currentBuild.rawBuild.getCauses()
    for (Cause cause in causes) {
        def user
        if (cause instanceof hudson.model.Cause.userIdCause) {
            hudson.model.Cause.UseridCause userIdCause = cause
            user = userIdCause.getuserName()
            return user
        }
    }
    return null
}

boolean isBuildcauseTriggerAction() {
    def causes = currentBuild.rawBuild.getCauses()
    for (Cause cause in causes) {
        if (cause instaceof hudson.model.Cause.userIdCause) return true
    }
    return false
}
boolean isBuildTriggeredByUser() {
    return isBuildCauseuserAction()
}
return this