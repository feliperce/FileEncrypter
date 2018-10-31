package encrypt

class CryptoException : Exception {

    constructor() {}

    constructor(message: String, throwable: Throwable) : super(message, throwable) {}
}