void sendMessage(String hangoutRoom, String result, String duration, String message, String startDate = '12:00:00') {
    String user = 'Jenkins'
    wrap([$class: 'BuildUser']) {
        try {
            user = BUILD_USER
        }
        catch (e) {
            print('User not in scope, probably triggered from another job')
        }
    }
    String endDate = new Date().format('dd/MM/yyyy HH:mm:ss z')
    String pipelineUrl = "${BUILD_URL}console"
    String msg = "*Pipeline:* ${JOB_BASE_NAME}\n*Build Number:*#${BUILD_NUMBER}\n*Started by user:* ${user}\n*Status:* ${result}\n*Start Time:* ${startDate}\n*End Time:* ${endDate}\n*Completion Time:* ${duration}\n*Message:* ${message}\n<${pipelineUrl}|Build Url>"
    googlechatnotification url:hangoutRoom , message: msg
}