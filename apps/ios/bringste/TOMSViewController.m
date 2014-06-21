//
//  TOMSViewController.m
//  bringste
//
//  Created by Tom König on 21/06/14.
//  Copyright (c) 2014 TomKnig. All rights reserved.
//

#import "TOMSViewController.h"
#import "Constants.h"
#import <MONActivityIndicatorView/MONActivityIndicatorView.h>

@interface TOMSViewController () <UIWebViewDelegate, MONActivityIndicatorViewDelegate>
@property (weak, nonatomic) IBOutlet UIWebView *webView;
@property (strong, nonatomic) MONActivityIndicatorView *indicatorView;
@property (assign, getter = isLoaded) BOOL loaded;
@end

@implementation TOMSViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self.view setBackgroundColor:[UIColor colorWithRed:0 green:0.639 blue:0.769 alpha:1]];
    
    self.indicatorView = [[MONActivityIndicatorView alloc] init];
    self.indicatorView.delegate = self;
    self.indicatorView.center = self.view.center;
    self.indicatorView.autoresizingMask = UIViewAutoresizingFlexibleTopMargin |
                                            UIViewAutoresizingFlexibleRightMargin |
                                            UIViewAutoresizingFlexibleBottomMargin |
                                            UIViewAutoresizingFlexibleLeftMargin;
    
    [self.view addSubview:self.indicatorView];
    
    self.webView.alpha = 0;
    self.webView.delegate = self;
    
    NSURL *url = [NSURL URLWithString:kBringsteURL];
    NSURLRequest *request = [[NSURLRequest alloc] initWithURL:url];
    [self.webView loadRequest:request];
}

#pragma mark - MONActivityIndicatorViewDelegate

- (UIColor *)activityIndicatorView:(MONActivityIndicatorView *)activityIndicatorView
      circleBackgroundColorAtIndex:(NSUInteger)index {
    return [UIColor whiteColor];
}

#pragma mark - Load indication

- (void)loadingDidStart
{
    [self.indicatorView startAnimating];
}

- (void)loadingDidEnd
{
    [self.indicatorView stopAnimating];
    [UIView animateWithDuration:0.37
                     animations:^{
                         self.view.backgroundColor = [UIColor whiteColor];
                         self.webView.alpha = 1;
                     }];
}

#pragma mark - UIWebViewDelegate

- (void)webViewDidStartLoad:(UIWebView *)webView
{
    if (!self.isLoaded) {
        [self loadingDidStart];
    }
}

- (void)webViewDidFinishLoad:(UIWebView *)webView
{
    if (!self.isLoaded) {
        [self loadingDidEnd];
        self.loaded = YES;
    }
}

@end
