export function getToken()
{
    if (!document.cookie) return ""

    const xsrfCookies = document.cookie.split(';')
        .map(c => c.trim())
        .filter(c => c.startsWith("XSRF-TOKEN" + '='))

    if (xsrfCookies.length === 0) return ""

    return decodeURIComponent(xsrfCookies[0].split('=')[1])
}