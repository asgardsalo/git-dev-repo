def call() {
    echo "Validating Terraform environment on macOS"
    sh '''
    set -euo pipefail

    echo "Checking for terraform..."
    if command -v terraform >/dev/null 2>&1; then
        echo "Terraform already installed:"
        terraform --version
        exit 0
    fi

    echo "Terraform not found. Attempting installation on macOS..."

    # Determine architecture
    ARCH="$(uname -m)"
    case "$ARCH" in
        x86_64) ARCH="amd64" ;;
        arm64|aarch64) ARCH="arm64" ;;
        *) echo "Unsupported architecture: $ARCH"; exit 1 ;;
    esac

    # Allow overriding version via environment variable TF_VERSION
    TF_VERSION="${TF_VERSION:-1.6.0}"

    # Try Homebrew if available
    if command -v brew >/dev/null 2>&1; then
        echo "Homebrew detected. Installing terraform via brew..."
        if brew list terraform >/dev/null 2>&1 || brew install terraform; then
            echo "Terraform installed via Homebrew:"
            terraform --version
            exit 0
        else
            echo "Homebrew install failed. Will try direct download."
        fi
    else
        echo "Homebrew not found. Will try direct download."
    fi

    # Fallback download from HashiCorp releases
    OS="darwin"
    ZIP_NAME="terraform_${TF_VERSION}_${OS}_${ARCH}.zip"
    DOWNLOAD_URL="https://releases.hashicorp.com/terraform/${TF_VERSION}/${ZIP_NAME}"

    echo "Downloading Terraform ${TF_VERSION} from ${DOWNLOAD_URL}..."
    tmpdir="$(mktemp -d)"
    cd "$tmpdir"

    if curl -fsSLO "${DOWNLOAD_URL}"; then
        unzip -q "${ZIP_NAME}"
        # Choose install dir based on common macOS locations
        if [ -d "/opt/homebrew/bin" ]; then
            INSTALL_DIR="/opt/homebrew/bin"
        else
            INSTALL_DIR="/usr/local/bin"
        fi

        echo "Installing terraform to ${INSTALL_DIR} (may require sudo)..."
        if [ -w "${INSTALL_DIR}" ]; then
            mv terraform "${INSTALL_DIR}/terraform"
            chmod +x "${INSTALL_DIR}/terraform"
        else
            sudo mv terraform "${INSTALL_DIR}/terraform"
            sudo chmod +x "${INSTALL_DIR}/terraform"
        fi

        echo "Terraform installed to ${INSTALL_DIR}"
        "${INSTALL_DIR}/terraform" --version
        cd -
        rm -rf "$tmpdir"
        exit 0
    else
        echo "Download failed. Unable to install Terraform automatically."
        cd -
        rm -rf "$tmpdir"
        exit 1
    fi
    '''
}

