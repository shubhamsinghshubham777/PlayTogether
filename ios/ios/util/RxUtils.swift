//
//  RxUtils.swift
//  ios
//
//  Created by Shubham Singh on 26/12/22.
//  Copyright © 2022 playtogether. All rights reserved.
//

import SwiftUI
import RxSwift
import shared
import KMPNativeCoroutinesRxSwift
import KMPNativeCoroutinesCore

typealias KotlinNativeFlow<T> = (@escaping (T, KotlinUnit) -> KotlinUnit, @escaping ((any Error)?, KotlinUnit) -> KotlinUnit) -> () -> KotlinUnit

public class ObservableValue<T>: ObservableObject {
    @Published
    var value: T
    
    private var disposable: Disposable?
    
    init(flow: @escaping KotlinNativeFlow<T>, initialValue: T) {
        self.value = initialValue
        disposable = createObservable(for: flow)
            .observe(on: MainScheduler.instance)
            .subscribe(onNext: { [weak self] state in
                self?.value = state
            })
    }
    
    deinit { disposable?.dispose() }
}
