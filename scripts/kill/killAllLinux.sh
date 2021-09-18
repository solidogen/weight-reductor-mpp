#!/bin/bash
cd ../..
/bin/bash scripts/kill/killFrontendServerLinux.sh
/bin/bash scripts/kill/killBackendServerLinux.sh
/bin/bash scripts/kill/killH2ServerLinux.sh
echo "all killed"